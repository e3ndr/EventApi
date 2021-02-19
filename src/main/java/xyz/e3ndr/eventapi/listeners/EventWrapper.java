package xyz.e3ndr.eventapi.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import xyz.e3ndr.eventapi.EventHelper;
import xyz.e3ndr.eventapi.events.AbstractEvent;

@Getter
@AllArgsConstructor
public class EventWrapper {
    private final @NonNull EventListener annotation;
    private final @NonNull Object listener;
    private final @NonNull Method method;

    public void call(@NonNull AbstractEvent<?> event) throws InvocationTargetException {
        if (EventHelper.checkListenerExecution(this.method, event, this.annotation)) {
            try {
                this.method.invoke(this.listener, event);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

}
