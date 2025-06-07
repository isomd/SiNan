package io.github.tml.core.persistence;

import java.nio.file.NoSuchFileException;

public interface Storage {
    String get(String key) throws NoSuchFileException;

    void set(String key, String value);

    Boolean remove(String key);

    Boolean containsKey(String key);
}
