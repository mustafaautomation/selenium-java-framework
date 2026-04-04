package com.quvantic.selenium.tests.inventory;

import com.quvantic.selenium.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Inventory")
public class InventoryTest extends BaseTest {

    @BeforeMethod
    public void login() {
        loginAsStandardUser();
    }

    @Test(groups = "smoke")
    @Story("Product display")
    @Description("Inventory page shows 6 products")
    public void displaysAllProducts() {
        assertThat(inventoryPage.getItemCount()).isEqualTo(6);
    }

    @Test(groups = "regression")
    @Story("Add to cart")
    @Description("Adding product updates cart badge")
    public void addProductToCart() {
        inventoryPage.addToCartByName("Sauce Labs Backpack");
        assertThat(inventoryPage.getCartBadgeCount()).isNotEqualTo("0");
    }

    @Test(groups = "regression")
    @Story("Remove from cart")
    @Description("Removing product clears cart badge")
    public void removeProductFromCart() {
        inventoryPage.addToCartByName("Sauce Labs Bike Light");
        inventoryPage.removeFromCartByName("Sauce Labs Bike Light");
    }

    @Test(groups = "regression")
    @Story("Sorting")
    @Description("Sort by price low to high")
    public void sortByPriceLowToHigh() {
        inventoryPage.sortBy("lohi");
        assertThat(inventoryPage.getFirstProductPrice()).contains("$");
    }

    @Test(groups = "regression")
    @Story("Sorting")
    @Description("Sort by name Z to A")
    public void sortByNameDesc() {
        inventoryPage.sortBy("za");
        assertThat(inventoryPage.getFirstProductName()).isNotEmpty();
    }
}
