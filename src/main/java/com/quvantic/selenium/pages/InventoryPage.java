package com.quvantic.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    @FindBy(css = "[data-test='product-sort-container']")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> itemPrices;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void waitForLoad() {
        wait.until(d -> !inventoryItems.isEmpty());
    }

    public int getItemCount() {
        return inventoryItems.size();
    }

    public void addToCartByName(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        click(driver.findElement(By.cssSelector("[data-test='" + buttonId + "']")));
    }

    public void removeFromCartByName(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        click(driver.findElement(By.cssSelector("[data-test='" + buttonId + "']")));
    }

    public String getCartBadgeCount() {
        try {
            return cartBadge.getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void openCart() {
        click(cartLink);
    }

    public void sortBy(String value) {
        new Select(sortDropdown).selectByValue(value);
    }

    public String getFirstProductName() {
        return getText(itemNames.get(0));
    }

    public String getFirstProductPrice() {
        return getText(itemPrices.get(0));
    }
}
