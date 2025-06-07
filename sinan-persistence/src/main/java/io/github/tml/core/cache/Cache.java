package io.github.tml.core.cache;

public interface Cache<K, V> {
    V get(K key);

    void set(K key, V value);

    String remove(K key);

    Boolean containsKey(K key);

    void clear();
}
