package xyz.e3ndr.eventapi.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCancellableEvent<T extends Enum<?>> extends AbstractEvent<T> {
    private boolean cancelled;

    public AbstractCancellableEvent(@NonNull T type) {
        super(type);
    }

}
