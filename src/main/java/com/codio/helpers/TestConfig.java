package com.codio.helpers;

public class TestConfig {

    public static String browser = AppConfig.browser;
    public static String headless = AppConfig.headless;
    public static String baseUrl = AppConfig.baseUrl;

    public static void initConfig() {
        browser = System.getProperty("browser") == null ? browser : System.getProperty("browser");
        headless = System.getProperty("headless") == null ? headless : System.getProperty("headless");
        baseUrl = System.getProperty("baseUrl") == null ? baseUrl : System.getProperty("baseUrl");
    }

    public static boolean isHeadless() {
        return headless.contains("true");
    }
}
