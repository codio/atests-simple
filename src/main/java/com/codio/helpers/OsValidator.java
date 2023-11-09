package com.codio.helpers;

public class OsValidator {

    private static String os = System.getProperty("os.name").toLowerCase();

    public static boolean isMac() {
        return os.contains("mac");
    }
}
