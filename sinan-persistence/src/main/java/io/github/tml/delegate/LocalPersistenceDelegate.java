package io.github.tml.delegate;

import com.alibaba.fastjson.JSONObject;
import io.github.tml.core.cache.Cache;
import io.github.tml.core.cache.DefaultCache;
import io.github.tml.core.persistence.LocalStorage;
import io.github.tml.core.persistence.Storage;

import java.nio.file.NoSuchFileException;

public class LocalPersistenceDelegate implements PersistenceDelegate {
    private final Cache<String, String> cache;

    private final Storage storage;

    public LocalPersistenceDelegate() {
        this.cache = new DefaultCache();
        this.storage = new LocalStorage();
    }

    @Override
    public Object get(String key) {
        if(cache.containsKey(key)){
            return JSONObject.parse(cache.get(key));
        } else {
            try {
                String data = storage.get(key);
                cache.set(key, data);
                return JSONObject.parse(data);
            } catch (NoSuchFileException e) {
                cache.set(key, null);
                return null;
            }
        }
    }

    @Override
    public void set(String key, Object value) {
        storage.set(key, JSONObject.toJSONString(value));
        cache.remove(key);
    }

    @Override
    public boolean remove(String key) {
        cache.remove(key);
        return storage.remove(key);
    }

    @Override
    public boolean containsKey(String key) {
        return cache.get(key) != null || storage.containsKey(key);
    }
}
