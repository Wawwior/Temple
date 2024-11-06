package me.wawwior.temple.registry;

import java.lang.reflect.Field;
import java.util.function.Supplier;

import com.google.common.reflect.TypeToken;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import me.wawwior.temple.Temple;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * RegistryHelper
 */
public class RegistryHelper {

    private final String modId;

    public RegistryHelper(String modId) {
        this.modId = modId;
    }

    public static <T, U> void registerClass(Class<?> clazz, Registrant<T, U> registrant, String modId) {
        for (Field field : clazz.getFields()) {
            if (field.getType() == Supplier.class) {
                try {

                    TypeToken<?> fieldType = TypeToken.of(field.getGenericType()).resolveType(Supplier.class.getTypeParameters()[0]);

                    Supplier<?> fieldValue = (Supplier<?>) field.get(null);

                    if (fieldType.equals(registrant.typeToken())) {
                        @SuppressWarnings("unchecked")
                        Supplier<T> supplier = () -> (T) fieldValue.get();
                        registrant.register(new AnnotationInfo(field), ResourceLocation.tryBuild(modId, field.getName().toLowerCase()), supplier);
                    }

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    Temple.LOGGER.info("How did we get here", e);
                }
            }
        }
    }

    public <T, U> void registerClass(Class<?> clazz, Registrant<T, U> registrant) {
        registerClass(clazz, registrant, modId);
    }

    public static <T> void register(ResourceKey<Registry<T>> key, ResourceLocation id, Supplier<T> value) {
        Registrar<T> registrar = registrarForMod(id.getNamespace()).get(key);
        registrar.register(id, value);
    }

    public static RegistrarManager registrarForMod(String modId) {
        return RegistrarManager.get(modId);
    }
}

