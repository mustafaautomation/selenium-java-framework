# Selenium Java Framework

[![E2E Tests](https://github.com/mustafaautomation/selenium-java-framework/actions/workflows/e2e-tests.yml/badge.svg)](https://github.com/mustafaautomation/selenium-java-framework/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg?logo=openjdk&logoColor=white)](https://openjdk.org)
[![Selenium](https://img.shields.io/badge/Selenium-4.51-43B02A.svg?logo=selenium&logoColor=white)](https://www.selenium.dev)

Selenium 4 + Java E2E test framework (portfolio project) with TestNG, Page Object Model and Allure reporting, running against the public SauceDemo site. Parallel execution with thread-safe WebDriver management; CI runs all tests headless and fails on any test failure.

---

## Test Coverage

| Suite | Tests | Group |
|-------|-------|-------|
| Login | 5 | smoke (4), regression (1) |
| Inventory | 5 | smoke (1), regression |
| Sorting | 5 | regression |
| Cart | 4 | smoke (1), regression |
| Checkout | 3 | regression |
| **Total** | **22** | 6 smoke |

---

## Quick Start

```bash
git clone https://github.com/mustafaautomation/selenium-java-framework.git
cd selenium-java-framework
# Needs JDK 21+, Maven 3.9+ and Chrome (or Firefox); Selenium Manager fetches the driver

# Run all tests (headless Chrome)
mvn clean test

# Run smoke tests only
mvn test -Dgroups=smoke

# Run with visible browser
mvn test -Dheadless=false

# Firefox
mvn test -Dbrowser=firefox

# Open the Allure report (needs the Allure CLI installed)
allure serve target/allure-results
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
│   ├── inventory/SortingTest.java    # 5 tests
│   ├── cart/CartTest.java            # 4 tests
│   └── checkout/CheckoutTest.java    # 3 tests
├── src/test/resources/testng.xml     # TestNG suite config
├── pom.xml
└── .github/workflows/e2e-tests.yml
```

---

## Stack

| Tool | Purpose |
|------|---------|
| Java 21 (LTS) | Language; CI also compiles on JDK 25 |
| Selenium 4.51 | Browser automation |
| TestNG 7.12 | Test runner with parallel + groups |
| Selenium Manager | Driver resolution (built into Selenium, no extra library) |
| Allure 2.35 | HTML reporting |
| AssertJ | Fluent assertions |
| Maven | Build + dependency management |

---

## License

MIT

---

Built by [Quvantic](https://quvantic.com)
