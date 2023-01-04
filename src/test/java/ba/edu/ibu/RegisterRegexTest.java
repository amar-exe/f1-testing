package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterRegexTest {
    private static WebDriver webDriver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", Config.path+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    void runRegisterRegexTest() throws InterruptedException {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToRegisterScreen(webDriver);

        CommonMethods.inputAllFieldsExceptPasswordOnRegisterScreen(webDriver);

        WebElement passwordField = webDriver.findElement(By.id("Password-input"));
        WebElement registerBtn = webDriver.findElement(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[2]/div[11]/div/div/div[1]/a"));
        registerBtn.click();
        WebElement minUppercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[1]"));
        WebElement minLowercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[2]"));
        WebElement minNumber = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[3]"));
        WebElement min8Char = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[4]"));
        WebElement minSpecial = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[5]"));

        testPasswordField(minUppercase, minLowercase, minNumber, min8Char, minSpecial, passwordField, registerBtn);

    }

    private boolean checkIfRed(WebElement webElement) {
        String currentColorMinLowercase = webElement.getCssValue("color");
        String color = Color.fromString(currentColorMinLowercase).asHex();
        return color.equals(Constants.errorColor);
    }

    private boolean checkIfRed(WebElement[] webElements) {
        for (WebElement element: webElements) {
            if(!checkIfRed(element)) {
                return false;
            };
        }
        return true;
    }

    private void testPasswordField(WebElement minUppercase,
                                   WebElement minLowercase, WebElement minNumber,
                                   WebElement min8Char, WebElement minSpecial,
                                   WebElement passwordField, WebElement registerBtn) {

        //Input one upercase letter
        passwordField.sendKeys("A");
        registerBtn.click();
        assertFalse(checkIfRed(minUppercase));
        assertTrue(checkIfRed(new WebElement[] {minLowercase, minNumber, min8Char, minSpecial}));

        //Input one lowercase letter
        passwordField.clear();
        passwordField.sendKeys("a");
        registerBtn.click();
        assertFalse(checkIfRed(minLowercase));
        assertTrue(checkIfRed(new WebElement[] {minUppercase, minNumber, min8Char, minSpecial}));

        //Input one number
        passwordField.clear();
        passwordField.sendKeys("1");
        registerBtn.click();
        assertFalse(checkIfRed(minNumber));
        assertTrue(checkIfRed(new WebElement[] {minUppercase, minLowercase, min8Char, minSpecial}));

        //Input one special character
        passwordField.clear();
        passwordField.sendKeys("!");
        registerBtn.click();
        assertFalse(checkIfRed(minSpecial));
        assertTrue(checkIfRed(new WebElement[] {minUppercase, minLowercase, min8Char, minNumber}));

        //Input at least 8 characters
        passwordField.clear();
        passwordField.sendKeys("12345678");
        registerBtn.click();
        assertFalse(checkIfRed(min8Char));
        assertTrue(checkIfRed(new WebElement[] {minUppercase, minLowercase, minSpecial}));

        //Input more than 30 characters
        passwordField.clear();
        passwordField.sendKeys("1234567891234567891234567891234");
        registerBtn.click();
        assertTrue(checkIfRed(min8Char));
        assertTrue(checkIfRed(new WebElement[] {minUppercase, minLowercase, minSpecial}));


    }
}
