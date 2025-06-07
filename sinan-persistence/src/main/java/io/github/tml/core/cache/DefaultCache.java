package io.github.tml.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCache implements Cache<String, String>{
    private Map<String, String> map;

    public DefaultCache() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public void set(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String remove(String key) {
        return map.remove(key);
    }

    @Override
    public Boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
