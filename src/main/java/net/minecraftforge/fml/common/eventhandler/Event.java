package net.minecraftforge.fml.common.eventhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Event {
    private boolean canceled;
    private Result result = Result.DEFAULT;

    public boolean isCancelable() {
        return getClass().isAnnotationPresent(Cancelable.class);
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean cancel) {
        canceled = cancel;
    }

    public boolean hasResult() {
        return getClass().isAnnotationPresent(HasResult.class);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result value) {
        result = value;
    }

    public enum Result {
        DENY,
        DEFAULT,
        ALLOW
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface HasResult {
    }
}
