package com.orangery.util;

import java.io.File;
import java.util.Objects;

public class ResourceUtil {

    public static File getResourceFile(String resourceName) {
        try {
            return new File(Objects.requireNonNull(
                    ResourceUtil.class.getClassLoader().getResource(resourceName)
            ).toURI());
        } catch (Exception e) {
            throw new RuntimeException("Resource not found: " + resourceName);
        }
    }
}