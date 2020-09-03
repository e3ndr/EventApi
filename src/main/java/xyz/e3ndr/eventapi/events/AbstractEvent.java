package xyz.e3ndr.eventapi.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEvent<T extends Enum<?>> {
    private final @NonNull T type;

}
