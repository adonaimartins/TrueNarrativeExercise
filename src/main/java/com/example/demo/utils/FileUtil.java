package com.example.demo.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileUtil {

    private FileUtil() {}

    @SneakyThrows
    public static String readResourceAsString(String path) {

        try (InputStream is = FileUtil.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException(path);
            }
            return IOUtils.toString(is, UTF_8);
        }
    }
}
