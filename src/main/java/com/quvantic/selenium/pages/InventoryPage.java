package com.quvantic.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        By add = productButton("add-to-cart-", productName);
        wait.until(ExpectedConditions.elementToBeClickable(add)).click();
        // The button re-renders as "Remove" once the item is in the cart; wait for that so the
        // next action does not race the re-render.
        wait.until(ExpectedConditions.visibilityOfElementLocated(productButton("remove-", productName)));
    }

    public void removeFromCartByName(String productName) {
        By remove = productButton("remove-", productName);
        wait.until(ExpectedConditions.elementToBeClickable(remove)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productButton("add-to-cart-", productName)));
    }

    private By productButton(String prefix, String productName) {
        return By.cssSelector("[data-test='" + prefix + productName.toLowerCase().replace(" ", "-") + "']");
    }

    public String getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(By.cssSelector(".shopping_cart_badge"));
        return badges.isEmpty() ? "0" : badges.get(0).getText();
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
