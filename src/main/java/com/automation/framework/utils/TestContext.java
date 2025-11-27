package com.automation.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe context for storing test data across steps in a scenario.
 * Uses ThreadLocal to ensure isolation between parallel test executions.
 */
public class TestContext {

    private static final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    /**
     * Store a value in the context.
     * @param key the key
     * @param value the value
     */
    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    /**
     * Retrieve a value from the context.
     * @param key the key
     * @return the value, or null if not found
     */
    public static Object get(String key) {
        return context.get().get(key);
    }

    /**
     * Retrieve a value from the context with a specific type.
     * @param key the key
     * @param clazz the class of the value
     * @return the value cast to the specified type, or null if not found
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        Object value = context.get().get(key);
        return value != null ? (T) value : null;
    }

    /**
     * Check if the context contains a key.
     * @param key the key
     * @return true if the key exists
     */
    public static boolean containsKey(String key) {
        return context.get().containsKey(key);
    }

    /**
     * Remove a value from the context.
     * @param key the key
     */
    public static void remove(String key) {
        context.get().remove(key);
    }

    /**
     * Clear the entire context for the current thread.
     */
    public static void clear() {
        context.get().clear();
    }

    /**
     * Clean up the ThreadLocal after test completion.
     */
    public static void cleanup() {
        context.remove();
    }
}