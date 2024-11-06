package me.wawwior.temple.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.reflect.TypeToken;

import me.wawwior.temple.registry.builtin.annotations.Id;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Registrant
 */
public abstract class Registrant<T, U> {
    private final List<Registrant<U, ?>> children = new ArrayList<>();

    private final TypeToken<T> type;

    private final Function<T, U> mapper;

    public Registrant(Class<T> type, Function<T, U> mapper) {
        this.type = TypeToken.of(type);
        this.mapper = mapper;
    }

    public void register(AnnotationInfo info, ResourceLocation id, Supplier<T> value) {
        Supplier<T> supplier = Suppliers.memoize(value::get);

        if (registerInternal(info, id, supplier)) {
            children.forEach(child -> child.register(info, id, Suppliers.memoize(() -> mapper.apply(supplier.get()))));
        }
    }

    protected abstract boolean registerInternal(AnnotationInfo info, ResourceLocation id, Supplier<T> value);

    public TypeToken<T> typeToken() {
        return type;
    }

    public <V> Registrant<T, U> with(Registrant<U, V> next) {
        children.add(next);
        return this;
    }

    public static <T, U> Registrant<T, U> of(Class<T> type, ResourceKey<Registry<U>> key, Function<T, U> mapper, Predicate<AnnotationInfo> annotationPredicate) {
        return new Registrant<T, U>(type, mapper) {

            @Override
            protected boolean registerInternal(AnnotationInfo info, ResourceLocation id, Supplier<T> value) {
                if (annotationPredicate.test(info)) {
                    info.getAnnotation(Id.class).ifPresentOrElse(
                            anno -> RegistryHelper.register(key, ResourceLocation.tryBuild(id.getNamespace(), anno.name()), () -> mapper.apply(value.get())),
                            () -> RegistryHelper.register(key, id, () -> mapper.apply(value.get()))
                        );
                    return true;
                } else {
                    return false;
                }
            }

        };
    }

    public static <T, U> Registrant<T, U> of(Class<T> type, ResourceKey<Registry<U>> key, Function<T, U> mapper) {
        return of(type, key, mapper, a -> true);
    }

    public static <T> Registrant<T, T> simple(Class<T> type, ResourceKey<Registry<T>> key, Predicate<AnnotationInfo> annotationPredicate) {
        return of(type, key, Function.identity(), annotationPredicate);
    }

    public static <T> Registrant<T, T> simple(Class<T> type, ResourceKey<Registry<T>> key) {
        return simple(type, key, a -> true);
    }

}
