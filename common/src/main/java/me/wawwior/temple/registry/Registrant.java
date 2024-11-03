package me.wawwior.temple.registry;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Registrant
 */
public interface Registrant<T, U> {

    void register(Class<T> type, ResourceKey<Registry<U>> key, Function<T, U> mapper);

    static <T, U> Consumer<Registrant<T, U>> of(Class<T> type, ResourceKey<Registry<U>> key, Function<T, U> mapper) {
        return registrant -> registrant.register(type, key, mapper);
    }

    static <T> Consumer<Registrant<T, T>> simple(Class<T> type, ResourceKey<Registry<T>> key) {
        return registrant -> registrant.register(type, key, Function.identity());
    }

}
