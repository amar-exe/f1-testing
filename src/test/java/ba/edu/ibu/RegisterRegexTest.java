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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

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
        webDriver.get(Config.baseUrl);

        goToRegisterScreen();

        inputAllFieldsExceptPassword();

        WebElement passwordField = webDriver.findElement(By.id("Password-input"));
        WebElement registerBtn = webDriver.findElement(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[2]/div[11]/div/div/div[1]/a"));
        registerBtn.click();
        WebElement minUppercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[1]"));
        WebElement minLowercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[2]"));
        WebElement minNumber = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[3]"));
        WebElement min8Char = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[4]"));
        WebElement minSpecial = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[5]"));

        testPasswordField(minUppercase, minLowercase, minNumber, min8Char, minSpecial, passwordField, registerBtn);

        testAccountCreation(passwordField, registerBtn);

    }

    private void goToRegisterScreen() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();

        WebElement goToSignIn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"globalNav\"]/div/div[2]/div[1]/div/a[1]"))

        );
        goToSignIn.click();

        WebElement goToRegister = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/header/div/div[2]/ul/li[2]/a"))

        );
        goToRegister.click();

        WebElement registerHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[1]/h2"))
        );
        assertEquals("CREATE ACCOUNT", registerHeader.getText());
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

    private String generateEmail() {
        int numOfLetters = 12;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(numOfLetters);

        for (int i = 0; i < numOfLetters; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return (sb + "@inboxkitten.com");
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

    private void testAccountCreation(WebElement passwordField, WebElement registerBtn) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        //Confirm register works
        passwordField.clear();
        passwordField.sendKeys("Test123!");
        registerBtn.click();
        Thread.sleep(6000);
        registerBtn.click();

        WebElement duplicateEmail = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//*[@id=\"registration-form\"]" +
                                        "/div/div/div/div/div[2]/div[7]/div/div/div/span"))
        );

        assertTrue(duplicateEmail.getText().contains("An account has already been created with this email address."));
    }



    private void inputAllFieldsExceptPassword() {
        Select titleSelect = new Select(webDriver.findElement(By.id("Title-input")));
        titleSelect.selectByValue("Mr");

        webDriver.findElement(By.xpath("//*[@id=\"FirstName-input\"]")).sendKeys("testingName");
        webDriver.findElement(By.xpath("//*[@id=\"LastName-input\"]")).sendKeys("testingSurname");
        WebElement dateInput = webDriver.findElement(By.id("BirthDate-input"));
        dateInput.clear();
        dateInput.click();
        dateInput.sendKeys("02012000");

        Select countrySelect = new Select(webDriver.findElement(By.id("Country-input")));
        countrySelect.selectByValue("BIH");
        webDriver.findElement(By.xpath("//*[@id=\"Email-input\"]")).sendKeys(generateEmail());
    }
}
