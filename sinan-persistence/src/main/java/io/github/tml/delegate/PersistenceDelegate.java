package io.github.tml.delegate;

public interface PersistenceDelegate {
    Object get(String key);

    void set(String key, Object value);

    boolean remove(String key);

    boolean containsKey(String key);
}
