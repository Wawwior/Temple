package me.wawwior.temple.registry;

/**
 * RConsumer
 */
public interface RegistrantConsumer<T, U> {

    void accept(Registrant<T, U> registrant);

}
