package me.wawwior.temple.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;

import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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

    @SuppressWarnings("unchecked")
    public static <T, U> void registerClass(Class<?> clazz, Registrant<T, U> registrant, String modId) {

        for (Field field : clazz.getFields()) {

            Optional<Pair<ResourceLocation, T>> fieldValue = Optional.empty();

            if (registrant.getType().isAssignableFrom(field.getType())) {
                try {
                    fieldValue = Optional.of(Pair.of(ResourceLocation.tryBuild(modId, field.getName().toLowerCase()), (T) field.get(null)));
                } catch (IllegalArgumentException | IllegalAccessException ignore) {}
            }

            fieldValue.ifPresent(pair -> registrant.register(pair.getFirst(), pair.getSecond()));
        }
    }

    public <T, U> void registerClass(Class<?> clazz, Registrant<T, U> registrant) {
        registerClass(clazz, registrant, modId);
    }

    public static <T> void register(ResourceKey<Registry<T>> key, ResourceLocation id, T value) {
        registrarForMod(id.getNamespace()).get(key).register(id, () -> value);
    }

    public static RegistrarManager registrarForMod(String modId) {
        return managers.computeIfAbsent(modId, id -> RegistrarManager.get(id));
    }
}

