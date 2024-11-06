package me.wawwior.temple.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class AnnotationInfo {

    private final Field field;

    public AnnotationInfo(Field field) {
        this.field = field;
    }

    public boolean hasAnnotation(Class<? extends Annotation> clazz) {
        return field.getAnnotation(clazz) != null;
    }

    public <T extends Annotation> Optional<T> getAnnotation(Class<T> clazz) {
        return Optional.ofNullable(field.getAnnotation(clazz));
    }

}

