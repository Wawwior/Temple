package me.wawwior.temple.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.mojang.datafixers.util.Pair;

import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceLocation;

/**
 * RegistryHelper
 */
public class RegistryHelper {

    private final String modId;

    private static final Map<String, RegistrarManager> managers = new HashMap<>();

    public RegistryHelper(String modId) {
        this.modId = modId;
    }

    public static <T, U> void register(Class<?> clazz, RegistrantConsumer<T, U> consumer, String modId) {
        consumer.accept((type, key, mapper) -> {

            FieldHandler<Pair<String, T>> fieldHandler = handlerForType(type);
            Registrar<U> registrar = registrarForMod(modId).get(key);

            for (Field field : clazz.getFields()) {

                fieldHandler.apply(field)
                            .map(pair -> Pair.of(pair.getFirst(), mapper.apply(pair.getSecond())))
                            .ifPresent(pair -> registrar.register(ResourceLocation.tryBuild(modId, pair.getFirst()), () -> pair.getSecond()));

            }
        });
    }

    public <T, U> void register(Class<?> clazz, RegistrantConsumer<T, U> consumer) {
        register(clazz, consumer, modId);
    }

    public static RegistrarManager registrarForMod(String modId) {
        return managers.computeIfAbsent(modId, id -> RegistrarManager.get(id));
    }

    private static <T> FieldHandler<Pair<String, T>> handlerForType(Class<T> type) {
        return FieldHandler.forType(type, (field, t) -> Pair.of(field.getName().toLowerCase(), t));
    }

}

