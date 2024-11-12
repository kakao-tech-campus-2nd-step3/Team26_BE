package org.ktc2.cokaen.wouldyouin._global;

public class TestUtil {

    public static <T> T getLeftOrRight(T left, T right) {
        return Math.random() > 0.5 ? left : right;
    }

    public static <T> T getOrNull(T object) {
        return getLeftOrRight(object, null);
    }
}
