package br.com.segment.leosmusicstoreapi.helpers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.text.CaseUtils;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapsHelper {

    /**
     * Converts an object to a HashMap. Useful to build events
     * to be sent through Segment Analytics instance.
     *
     * @param instance The object to be converted to a HashMap
     * @param <T>      Any class, but preferably a model or DTO.
     * @return A HashMap with the converted object.
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> objectToMap(T instance)
            throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> objectAsDict = new HashMap<>();
        Method[] allMethods = instance.getClass().getMethods();
        for (Method method : allMethods) {
            if (!method.getName().startsWith("get")) {
                continue;
            }

            boolean skipField = false;
            String correspondingFieldName = CaseUtils.toCamelCase(
                    method.getName().replaceFirst("get", ""),
                    false,
                    ' ');

            // Fields marked with @JsonIgnore should *not* be reported.
            // For instance, `User.passwordHash`.
            Optional<Field> attemptedField = ReflectionHelper.getField(instance, correspondingFieldName);
            if (attemptedField.isPresent()) {
                if (attemptedField.get().isAnnotationPresent(JsonIgnore.class)) {
                    skipField = true;
                }
            }

            Object value = method.invoke(instance);
            if (value.getClass().isAnnotationPresent(Entity.class)) {
                objectAsDict.put(method.getName(), objectToMap(value));
            } else if (!skipField) {
                objectAsDict.put(method.getName(), value.toString());
            }
        }

        return objectAsDict;
    }
}
