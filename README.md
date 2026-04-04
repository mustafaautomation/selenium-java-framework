# Selenium Java Framework

[![E2E Tests](https://github.com/mustafaautomation/selenium-java-framework/actions/workflows/e2e-tests.yml/badge.svg)](https://github.com/mustafaautomation/selenium-java-framework/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-ED8B00.svg?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Selenium](https://img.shields.io/badge/Selenium-4.27-43B02A.svg?logo=selenium&logoColor=white)](https://www.selenium.dev)

Enterprise Selenium 4 E2E testing framework with TestNG, Page Object Model, and Allure reporting. Parallel execution, thread-safe WebDriver management, and CI/CD ready.

---

## Test Coverage

| Suite | Tests | Group |
|-------|-------|-------|
| Login | 5 | smoke |
| Inventory | 5 | regression |
| Checkout | 3 | regression |
| **Total** | **13** | |

---

## Quick Start

```bash
git clone https://github.com/mustafaautomation/selenium-java-framework.git
cd selenium-java-framework

# Run all tests (headless Chrome)
mvn clean test

# Run smoke tests only
mvn test -Dgroups=smoke

# Run with visible browser
mvn test -Dheadless=false

# Firefox
mvn test -Dbrowser=firefox

# Generate Allure report
mvn allure:serve
```

---

## Architecture

```
┌─────────────────────────────────────────┐
│           Test Classes                   │
│  LoginTest │ InventoryTest │ ...         │
├─────────────────────────────────────────┤
│           BaseTest                       │
│  setUp/tearDown │ loginAsStandardUser()  │
├─────────────────────────────────────────┤
│           Page Objects (@FindBy)         │
│  LoginPage │ InventoryPage │ CartPage    │
│  CheckoutPage │ BasePage                 │
├─────────────────────────────────────────┤
│           Infrastructure                 │
│  DriverFactory (ThreadLocal)             │
│  TestConfig (System properties)          │
├─────────────────────────────────────────┤
│  Selenium 4 │ TestNG │ Allure │ AssertJ  │
└─────────────────────────────────────────┘
```

---

## Key Patterns

### Thread-Safe WebDriver
`DriverFactory` uses `ThreadLocal<WebDriver>` for safe parallel execution:

```java
private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
```

### Page Object Model with @FindBy
Each page uses Selenium's `PageFactory` with `@FindBy` annotations:

```java
@FindBy(id = "user-name")
private WebElement usernameInput;
```

### BasePage Utilities
Common wait/click/type methods in `BasePage`:

```java
protected void click(WebElement element) {
    waitForClickable(element);
    element.click();
}
```

### Parallel Execution
TestNG runs test classes in parallel (3 threads):

```xml
<suite parallel="classes" thread-count="3">
```

---

## Project Structure

```
selenium-java-framework/
├── src/main/java/com/quvantic/selenium/
│   ├── config/TestConfig.java        # Env-based config
│   ├── utils/DriverFactory.java      # ThreadLocal WebDriver
│   └── pages/
│       ├── BasePage.java             # Wait/click/type helpers
│       ├── LoginPage.java
│       ├── InventoryPage.java
│       ├── CartPage.java
│       └── CheckoutPage.java
├── src/test/java/com/quvantic/selenium/tests/
│   ├── BaseTest.java                 # Setup/teardown
│   ├── auth/LoginTest.java           # 5 tests
│   ├── inventory/InventoryTest.java  # 5 tests
│   └── checkout/CheckoutTest.java    # 3 tests
├── src/test/resources/testng.xml     # TestNG suite config
├── pom.xml
└── .github/workflows/e2e-tests.yml
```

---

## Stack

| Tool | Purpose |
|------|---------|
| Selenium 4.27 | Browser automation |
| TestNG 7.10 | Test runner with parallel + groups |
| WebDriverManager | Automatic driver management |
| Allure 2.29 | Rich HTML reporting |
| AssertJ | Fluent assertions |
| Maven | Build + dependency management |

---

## License

MIT

---

Built by [Quvantic](https://quvantic.com)
