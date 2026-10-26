package com.quvantic.selenium.utils;

import com.quvantic.selenium.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(createDriver());
        }
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static WebDriver createDriver() {
        String browser = TestConfig.getBrowser().toLowerCase();
        boolean headless = TestConfig.isHeadless();

        return switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("--headless");
                yield new FirefoxDriver(options);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                // SauceDemo's public password is in Chrome's leaked-password list. Chrome's password
                // manager then opens a "change your password" bubble after login that swallows the
                // next clicks, so turn the password manager and leak detection off for test runs.
                options.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
                options.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false,
                        "profile.password_manager_leak_detection", false));
                yield new ChromeDriver(options);
            }
        };
    }
}
