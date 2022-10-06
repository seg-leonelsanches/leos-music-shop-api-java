package br.com.segment.leosmusicstoreapi.helpers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.util.Optional;

public class ReflectionHelper {
    /**
     * Calls the first iteration of `getFieldFromType()`, a recursive method.
     *
     * @param instance  The object we are investigating.
     * @param fieldName The field we are investigating.
     * @param <T>       The instance type.
     * @return An `Optional<Field>`, which value is present if the field is found.
     * Empty otherwise.
     */
    public static <T> Optional<Field> getField(T instance, String fieldName) {
        Class<?> instanceClass = instance.getClass();
        return getFieldFromType(instanceClass, fieldName);
    }

    /**
     * Searches in the current class for a field.
     * If field is found, returns the field.
     * Otherwise, it tries the superclass, if superclass is set.
     * This is a recursive method.
     *
     * @param classType The class type.
     * @param fieldName The field name.
     * @param <T>       The instance type.
     * @return An `Optional<Field>`, which value is present if the field is found.
     * Empty otherwise.
     */
    private static <T> Optional<Field> getFieldFromType(Class<T> classType, String fieldName) {
        Field[] fields = classType.getDeclaredFields();

        if (fields.length == 0) {
            return Optional.empty();
        }

        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase(fieldName)) {
                continue;
            }

            if (field.isAnnotationPresent(JsonIgnore.class)) {
                return Optional.of(field);
            }
        }

        Class<? super T> superclass = classType.getSuperclass();
        if (superclass != null) {
            return getFieldFromType(superclass, fieldName);
        }

        return Optional.empty();
    }
}
