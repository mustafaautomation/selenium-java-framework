package com.quvantic.selenium.tests.cart;

import com.quvantic.selenium.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Cart")
public class CartTest extends BaseTest {

    @BeforeMethod
    public void loginAndAddProducts() {
        loginAsStandardUser();
    }

    @Test(groups = "smoke")
    @Story("Cart display")
    @Description("Cart page shows added items")
    public void cartShowsAddedItems() {
        inventoryPage.addToCartByName("Sauce Labs Backpack");
        inventoryPage.addToCartByName("Sauce Labs Bike Light");
        inventoryPage.openCart();

        assertThat(cartPage.getItemCount()).isEqualTo(2);
    }

    @Test(groups = "regression")
    @Story("Cart contains product")
    @Description("Cart displays correct product name")
    public void cartContainsCorrectProduct() {
        inventoryPage.addToCartByName("Sauce Labs Bolt T-Shirt");
        inventoryPage.openCart();

        assertThat(cartPage.containsProduct("Sauce Labs Bolt T-Shirt")).isTrue();
    }

    @Test(groups = "regression")
    @Story("Empty cart")
    @Description("Cart is empty when no products added")
    public void emptyCartShowsZeroItems() {
        inventoryPage.openCart();

        assertThat(cartPage.getItemCount()).isEqualTo(0);
    }

    @Test(groups = "regression")
    @Story("Cart checkout")
    @Description("Checkout button navigates to checkout step one")
    public void checkoutFromCart() {
        inventoryPage.addToCartByName("Sauce Labs Backpack");
        inventoryPage.openCart();
        cartPage.checkout();

        assertThat(loginPage.getCurrentUrl()).contains("checkout-step-one");
    }
}
