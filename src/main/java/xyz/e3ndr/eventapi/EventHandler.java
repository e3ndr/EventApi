package xyz.e3ndr.eventapi;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import lombok.NonNull;
import xyz.e3ndr.eventapi.events.AbstractEvent;
import xyz.e3ndr.eventapi.listeners.EventPriority;
import xyz.e3ndr.eventapi.listeners.EventWrapper;

public class EventHandler<T extends Enum<?>> {
    private MultiValuedMap<EventPriority, EventWrapper> wrappers = new HashSetValuedHashMap<>();

    public void register(@NonNull Object listener) {
        this.wrappers.putAll(EventHelper.wrap(listener));
    }

    public void call(@NonNull AbstractEvent<T> event) {
        for (EventPriority priority : EventPriority.values()) {
            for (EventWrapper wrapper : this.wrappers.get(priority)) {
                wrapper.call(event);
            }
        }
    }

}
