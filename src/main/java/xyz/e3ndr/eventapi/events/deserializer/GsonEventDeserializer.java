package xyz.e3ndr.eventapi.events.deserializer;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.NonNull;
import xyz.e3ndr.eventapi.events.AbstractEvent;

public class GsonEventDeserializer<T extends Enum<?>> {
    private Map<T, Class<? extends AbstractEvent<T>>> eventClasses = new HashMap<>();
    private Gson gson;

    public GsonEventDeserializer() {
        this(new Gson());
    }

    public GsonEventDeserializer(@NonNull Gson gson) {
        this.gson = gson;
    }

    public void registerEventClass(@NonNull T type, @NonNull Class<? extends AbstractEvent<T>> clazz) {
        this.eventClasses.put(type, clazz);
    }

    public AbstractEvent<T> deserializeJson(@NonNull T type, @NonNull JsonElement e) {
        Class<? extends AbstractEvent<T>> clazz = this.eventClasses.get(type);

        if (clazz == null) {
            throw new IllegalArgumentException("No registered class for type: " + type);
        } else {
            return this.gson.fromJson(e, clazz);
        }
    }

    public static <E extends Enum<E>> Enum<E> parseEnumFromJsonElement(Enum<E>[] values, JsonElement e) {
        String value = e.getAsString();

        for (Enum<E> en : values) {
            if (en.name().equalsIgnoreCase(value)) {
                return en;
            }
        }

        throw new IllegalArgumentException("Unknown enum type: " + value);
    }

}
