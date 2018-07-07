package com.yinheng.interfacetester.util;

public class TextUtils {

    // 1 null 2 ""
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
