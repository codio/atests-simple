package com.codio.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class AppConfig {

    public static String baseUrl = "prod";
    public static String browser = "chrome"; // browsers: "chrome", "firefox"
    public static String headless = "false"; // run tests mode: "false" - show browser, "true" - don't show browser
    public static int parallel = 2;
    public static int timeOutSec = 3;
    public static int timeOutSecMedium = 10;
    public static int timeOutSecLong = 60;
    public static String prod = "https://codio.com";
    public static HashMap<String, String> environment = new HashMap<>();

    static {

        Properties properties = new Properties();

        try {
            FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties");
            try {
                properties.load(inputStream);
                baseUrl = properties.getProperty("baseurl", baseUrl);
                browser = properties.getProperty("browser", browser);
                headless = properties.getProperty("headless", headless);
                parallel = Integer.parseInt(properties.getProperty("parallel", String.valueOf(parallel)));
                timeOutSec = Integer.parseInt(properties.getProperty("timeOutSec", String.valueOf(timeOutSec)));
                timeOutSecMedium =
                    Integer.parseInt(properties.getProperty("timeOutSecMedium", String.valueOf(timeOutSecMedium)));
                timeOutSecLong =
                    Integer.parseInt(properties.getProperty("timeOutSecLong", String.valueOf(timeOutSecLong)));
                prod = properties.getProperty("prod", prod);
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        environment.put("prod", prod);
    }
}
