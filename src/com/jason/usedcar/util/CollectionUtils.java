package com.jason.usedcar.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author t77yq @2014-11-08.
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}

