package io.github.tml.core.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

public class LocalStorage implements Storage{
    private static final Logger log = LoggerFactory.getLogger(LocalStorage.class);
    private static final String BASE = "src/main/java/io/github/tml/data/";

    @Override
    public String get(String key) throws NoSuchFileException {
        return readFile(generatePath(key));
    }

    @Override
    public void set(String key, String value) {
        synchronized (key.intern()){
            writeFile(generatePath(key), value);
        }
    }

    @Override
    public Boolean remove(String key) {
        Path path = Paths.get(generatePath(key));
        try {
            // 检查文件是否存在，并删除
            if (Files.exists(path)) {
                Files.delete(path);
            }
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean containsKey(String key) {
        Path path = Paths.get(generatePath(key));
        return (Boolean) Files.exists(path);
    }

    private String generatePath(String key) {
        return BASE + key.replace(".", "/") + "data.txt";
    }

    private String readFile(String path) throws NoSuchFileException {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            lines.forEach(line -> sb.append(line).append("\n"));
        } catch (NoSuchFileException e) {
            log.error("File Not Found, Path:{}", path);
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void writeFile(String path, String data) {
        try {
            // 写入内容到文件，如果文件已存在则覆盖
            Files.write(Paths.get(path), data.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
