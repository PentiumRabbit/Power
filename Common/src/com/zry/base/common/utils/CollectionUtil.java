package com.zry.base.common.utils;

import java.util.Collection;

public class CollectionUtil {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int size(Collection collection) {
        return collection == null ? 0 : collection.size();

    }

    public static <T, O> boolean contains(T[] collection, O o) {
        if (collection == null ||
                collection.length == 0 ||
                o == null) {
            return false;
        }

        for (T t : collection) {
            if (o.equals(t)) return true;
        }
        return false;
    }

}
