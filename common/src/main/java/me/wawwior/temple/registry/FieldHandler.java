package me.wawwior.temple.registry;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * FieldHandler
 */
public abstract class FieldHandler<T> implements Function<Field, Optional<T>> {

    /**
     * @param <T> The type of the targeted fields
     * @param <U> The type of the mapped results
     * @param type The class of T, for safe casting
     * @param map The function that maps T -> U, will only be called for fields with type T (T will never be null due to conversion loss).
     * @return returns a FieldHandler that takes fields of type T and returns their value mapped with given map
     */
    public static final <T, U> FieldHandler<U> forType(Class<T> type, BiFunction<Field, T, U> map) {
        return new FieldHandler<U>() {

            @Override
            public Optional<U> apply(Field field) {
                if (type.isAssignableFrom(field.getType())) {
                    try {
                        return Optional.of(map.apply(field, type.cast(field.get(null))));
                    } catch (IllegalArgumentException | IllegalAccessException ignore) {}
                }

                return Optional.empty();
            }

        };
    }
}
