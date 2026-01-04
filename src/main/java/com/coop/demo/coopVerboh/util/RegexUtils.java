package com.coop.demo.coopVerboh.util;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexUtils {

    // Safe regex match
    public static boolean matches(String pattern, String text) {
        try {
            return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text).matches();
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid regex: " + pattern + " -> " + e.getMessage());
            return false;
        }
    }
}
