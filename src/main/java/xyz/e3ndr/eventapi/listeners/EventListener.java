package xyz.e3ndr.eventapi.listeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.NonNull;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
    @NonNull
    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default false;

}
