package ba.edu.ibu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonMethods {
    public static void acceptMarketingCookies(WebDriver webDriver){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();
    }

    public static void goToRegisterScreen(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

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
    public static void goToTicketsScreen(WebDriver webDriver){
        webDriver.findElement(By.xpath("//*[@id=\"globalNav\"]/div/div[2]/div[2]/ul/li[4]/a")).click();
        ArrayList<String> switchTabs= new ArrayList<String> (webDriver.getWindowHandles());
        webDriver.switchTo().window(switchTabs.get(0));
        webDriver.close();
        webDriver.switchTo().window(switchTabs.get(1));
    }
    public static void acceptTicketsScreenCookies(WebDriver webDriver){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"))
        );
        acceptCookies.click();
    }
    public static String generateEmail() {
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

    public static void inputAllFieldsExceptPasswordOnRegisterScreen(WebDriver webDriver) {
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
        webDriver.findElement(By.xpath("//*[@id=\"Email-input\"]")).sendKeys(CommonMethods.generateEmail());
    }

    public static void testAccountCreation(WebDriver webDriver) throws InterruptedException {
        WebElement passwordField = webDriver.findElement(By.id("Password-input"));
        WebElement registerBtn = webDriver.findElement(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[2]/div[11]/div/div/div[1]/a"));
        CommonMethods.inputAllFieldsExceptPasswordOnRegisterScreen(webDriver);
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
}
