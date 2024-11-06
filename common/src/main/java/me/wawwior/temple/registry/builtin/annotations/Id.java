package me.wawwior.temple.registry.builtin.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Id
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Id {

    String name();

}
