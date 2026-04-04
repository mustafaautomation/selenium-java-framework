package com.quvantic.selenium.tests.auth;

import com.quvantic.selenium.config.TestConfig;
import com.quvantic.selenium.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    @BeforeMethod
    public void navigateToLogin() {
        loginPage.navigate();
    }

    @Test(groups = "smoke")
    @Story("Valid login")
    @Description("Standard user can log in and reach inventory page")
    public void validLogin() {
        loginPage.login(TestConfig.getStandardUser(), TestConfig.getPassword());
        assertThat(loginPage.getCurrentUrl()).contains("inventory");
    }

    @Test(groups = "smoke")
    @Story("Locked user")
    @Description("Locked out user sees appropriate error")
    public void lockedOutUser() {
        loginPage.login("locked_out_user", TestConfig.getPassword());
        assertThat(loginPage.getErrorText()).contains("locked out");
    }

    @Test(groups = "smoke")
    @Story("Empty username")
    @Description("Empty username shows validation error")
    public void emptyUsername() {
        loginPage.login("", TestConfig.getPassword());
        assertThat(loginPage.getErrorText()).contains("Username is required");
    }

    @Test(groups = "smoke")
    @Story("Empty password")
    @Description("Empty password shows validation error")
    public void emptyPassword() {
        loginPage.login(TestConfig.getStandardUser(), "");
        assertThat(loginPage.getErrorText()).contains("Password is required");
    }

    @Test(groups = "regression")
    @Story("Invalid credentials")
    @Description("Invalid credentials show error")
    public void invalidCredentials() {
        loginPage.login("bad_user", "bad_pass");
        assertThat(loginPage.getErrorText()).contains("do not match");
    }
}
