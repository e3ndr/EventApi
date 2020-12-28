package xyz.e3ndr.eventapi;

import java.lang.reflect.Method;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import lombok.NonNull;
import xyz.e3ndr.eventapi.events.AbstractCancellableEvent;
import xyz.e3ndr.eventapi.events.AbstractEvent;
import xyz.e3ndr.eventapi.listeners.EventListener;
import xyz.e3ndr.eventapi.listeners.EventPriority;
import xyz.e3ndr.eventapi.listeners.EventWrapper;

public class EventHelper {

    public static MultiValuedMap<EventPriority, EventWrapper> wrap(@NonNull Object listener) {
        MultiValuedMap<EventPriority, EventWrapper> wrappers = new HashSetValuedHashMap<>();

        for (Method method : listener.getClass().getMethods()) {
            if (isListenerMethod(method)) {
                EventListener annotation = method.getAnnotation(EventListener.class);
                EventWrapper wrapper = new EventWrapper(annotation, listener, method);

                wrappers.put(annotation.priority(), wrapper);
            }
        }

        return wrappers;
    }

    public static boolean isListenerMethod(@NonNull Method method) {
        return method.isAnnotationPresent(EventListener.class) && (method.getParameterCount() == 1);
    }

    public static boolean checkListenerExecution(@NonNull Method method, @NonNull AbstractEvent<?> event, @NonNull EventListener annotation) {
        if (!method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
            return false;
        } else if (event instanceof AbstractCancellableEvent<?>) {
            if (((AbstractCancellableEvent<?>) event).isCancelled() && annotation.ignoreCancelled()) {
                return false;
            }
        }

        return true;
    }

}
