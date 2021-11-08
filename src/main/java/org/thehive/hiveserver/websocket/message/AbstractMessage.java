package org.thehive.hiveserver.websocket.message;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class AbstractMessage<T> implements Message<T> {

    private Map<String, Object> headers;

    protected AbstractMessage(@NonNull Map<String, Object> headers) {
        this.headers = headers;
    }

    protected AbstractMessage() {
        this(new HashMap<>(4));
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void addHeader(@NonNull String key, @NonNull Object value) {
        this.headers.put(key, value);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <V> V getHeader(@NonNull String key) {
        return (V) headers.get(key);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <V> V getHeader(@NonNull String key, @NonNull Class<V> type) {
        return (V) headers.get(key);
    }

    public boolean containsHeader(@NonNull String key) {
        return headers.containsKey(key);
    }

}
