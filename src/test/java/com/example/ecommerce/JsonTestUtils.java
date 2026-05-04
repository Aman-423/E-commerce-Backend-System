package com.example.ecommerce;

import com.jayway.jsonpath.JsonPath;

public final class JsonTestUtils {

    private JsonTestUtils() {
    }

    public static long readLong(String json, String path) {
        Number value = JsonPath.read(json, path);
        return value.longValue();
    }
}
