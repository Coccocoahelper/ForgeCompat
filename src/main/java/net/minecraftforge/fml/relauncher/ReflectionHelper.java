package net.minecraftforge.fml.relauncher;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Field findField(Class<?> owner, String... names) {
        Exception failed = null;
        for (String name : names) {
            try {
                Field field = owner.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                failed = e;
            }
        }
        throw new UnableToFindFieldException(names, failed);
    }

    public static class UnableToFindFieldException extends RuntimeException {
        public final String[] fieldNames;

        public UnableToFindFieldException(String[] fieldNames, Exception cause) {
            super(cause);
            this.fieldNames = fieldNames;
        }
    }
}
