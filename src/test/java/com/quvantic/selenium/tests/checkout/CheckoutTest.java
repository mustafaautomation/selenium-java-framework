package com.quvantic.selenium.tests.checkout;

import com.quvantic.selenium.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    @BeforeMethod
    public void loginAndAddProduct() {
        loginAsStandardUser();
        inventoryPage.addToCartByName("Sauce Labs Backpack");
    }

    @Test(groups = "regression")
    @Story("Complete checkout")
    @Description("Full checkout flow with single item")
    public void completeCheckout() {
        inventoryPage.openCart();
        assertThat(cartPage.containsProduct("Sauce Labs Backpack")).isTrue();

        cartPage.checkout();
        checkoutPage.fillInfo("Test", "User", "12345");
        checkoutPage.clickContinue();

        assertThat(checkoutPage.getTotal()).contains("$");

        checkoutPage.clickFinish();
        assertThat(checkoutPage.isOrderComplete()).isTrue();
    }

    @Test(groups = "regression")
    @Story("Checkout validation")
    @Description("Checkout fails without first name")
    public void failsWithoutFirstName() {
        inventoryPage.openCart();
        cartPage.checkout();
        checkoutPage.fillInfo("", "User", "12345");
        checkoutPage.clickContinue();
        assertThat(checkoutPage.getErrorText()).contains("First Name is required");
    }

    @Test(groups = "regression")
    @Story("Checkout validation")
    @Description("Checkout fails without postal code")
    public void failsWithoutPostalCode() {
        inventoryPage.openCart();
        cartPage.checkout();
        checkoutPage.fillInfo("Test", "User", "");
        checkoutPage.clickContinue();
        assertThat(checkoutPage.getErrorText()).contains("Postal Code is required");
    }
}
