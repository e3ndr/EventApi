package xyz.e3ndr.eventapi.events.deserializer;

import java.util.HashMap;
import java.util.Map;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.NonNull;
import xyz.e3ndr.eventapi.events.AbstractEvent;

public class RsonEventDeserializer<T extends Enum<?>> {
    private Map<T, Class<? extends AbstractEvent<T>>> eventClasses = new HashMap<>();
    private Rson rson;

    public RsonEventDeserializer() {
        this(Rson.DEFAULT);
    }

    public RsonEventDeserializer(@NonNull Rson rson) {
        this.rson = rson;
    }

    public void registerEventClass(@NonNull T type, @NonNull Class<? extends AbstractEvent<T>> clazz) {
        this.eventClasses.put(type, clazz);
    }

    public AbstractEvent<T> deserializeJson(@NonNull T type, @NonNull JsonElement e) throws JsonValidationException, JsonParseException {
        Class<? extends AbstractEvent<T>> clazz = this.eventClasses.get(type);

        if (clazz == null) {
            throw new IllegalArgumentException("No registered class for type: " + type);
        } else {
            return this.rson.fromJson(e, clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<?>> E parseEnumFromJsonElement(E[] values, JsonElement e) {
        String value = e.getAsString();

        for (Enum<?> en : values) {
            if (en.name().equalsIgnoreCase(value)) {
                return (E) en;
            }
        }

        throw new IllegalArgumentException("Unknown enum type: " + value);
    }

}
