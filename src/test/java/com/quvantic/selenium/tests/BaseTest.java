package com.quvantic.selenium.tests;

import com.quvantic.selenium.config.TestConfig;
import com.quvantic.selenium.pages.*;
import com.quvantic.selenium.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    // alwaysRun so the driver is still set up when running a subset with -Dgroups=smoke
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected void loginAsStandardUser() {
        loginPage.navigate();
        loginPage.login(TestConfig.getStandardUser(), TestConfig.getPassword());
        inventoryPage.waitForLoad();
    }
}
