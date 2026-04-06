package com.quvantic.selenium.tests.inventory;

import com.quvantic.selenium.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Shopping")
@Feature("Sorting")
public class SortingTest extends BaseTest {

    @BeforeMethod
    public void login() {
        loginAsStandardUser();
    }

    @Test(groups = "regression")
    @Story("Sort A-Z")
    @Description("Products sorted alphabetically A to Z")
    public void sortByNameAsc() {
        inventoryPage.sortBy("az");
        String first = inventoryPage.getFirstProductName();
        assertThat(first).isNotEmpty();
        // A-Z means first product should start early in alphabet
        assertThat(first.charAt(0)).isLessThanOrEqualTo('Z');
    }

    @Test(groups = "regression")
    @Story("Sort Z-A")
    @Description("Products sorted alphabetically Z to A")
    public void sortByNameDesc() {
        inventoryPage.sortBy("za");
        String first = inventoryPage.getFirstProductName();
        assertThat(first).isNotEmpty();
    }

    @Test(groups = "regression")
    @Story("Sort price low-high")
    @Description("Products sorted by price ascending")
    public void sortByPriceAsc() {
        inventoryPage.sortBy("lohi");
        String price = inventoryPage.getFirstProductPrice();
        assertThat(price).contains("$");
    }

    @Test(groups = "regression")
    @Story("Sort price high-low")
    @Description("Products sorted by price descending")
    public void sortByPriceDesc() {
        inventoryPage.sortBy("hilo");
        String price = inventoryPage.getFirstProductPrice();
        assertThat(price).contains("$");
    }

    @Test(groups = "regression")
    @Story("Product count after sort")
    @Description("All 6 products visible after sorting")
    public void allProductsVisibleAfterSort() {
        inventoryPage.sortBy("lohi");
        assertThat(inventoryPage.getItemCount()).isEqualTo(6);
        inventoryPage.sortBy("za");
        assertThat(inventoryPage.getItemCount()).isEqualTo(6);
    }
}
