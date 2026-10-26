package com.quvantic.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_list")
    private WebElement cartList;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /** The cart is rendered client-side; wait for its list before reading it. */
    public void waitForLoad() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        waitForVisible(cartList);
    }

    public int getItemCount() {
        waitForLoad();
        return cartItems.size();
    }

    public boolean containsProduct(String productName) {
        return getText(cartList).contains(productName);
    }

    public void checkout() {
        click(checkoutButton);
    }
}
