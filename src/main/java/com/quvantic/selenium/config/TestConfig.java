package com.quvantic.selenium.config;

public final class TestConfig {

    private TestConfig() {}

    public static String getBaseUrl() {
        return System.getProperty("base.url", "https://www.saucedemo.com");
    }

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "true"));
    }

    public static int getTimeout() {
        return Integer.parseInt(System.getProperty("timeout", "10"));
    }

    public static String getStandardUser() {
        return System.getProperty("standard.user", "standard_user");
    }

    public static String getPassword() {
        return System.getProperty("password", "secret_sauce");
    }
}
