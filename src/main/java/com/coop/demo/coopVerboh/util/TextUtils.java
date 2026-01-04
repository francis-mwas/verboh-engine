package com.coop.demo.coopVerboh.util;


public class TextUtils {

    // Normalize text for intent matching
    public static String normalize(String text) {
        if (text == null) return "";
        return text.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim();
    }
}
