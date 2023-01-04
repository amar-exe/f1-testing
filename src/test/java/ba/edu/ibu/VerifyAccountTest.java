package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerifyAccountTest {
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
    void runVerifyAccountTest() throws InterruptedException {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToRegisterScreen(webDriver);

        String tempEmail = CommonMethods.generateEmail();

        CommonMethods.testAccountCreation(webDriver, tempEmail);

        goToInboxKitten(webDriver);

        changeEmailOnInboxKitten(webDriver, tempEmail);

        clickOnVerificationEmail(webDriver);

        clickOnVerifyLink(webDriver);

        checkVerificationSuccess(webDriver);
    }

    public void checkVerificationSuccess(WebDriver webDriver) throws InterruptedException {
        Thread.sleep(7000);

        ArrayList<String> switchTabs= new ArrayList<String> (webDriver.getWindowHandles());
        webDriver.switchTo().window(switchTabs.get(2));

        assertEquals(Constants.verificationSuccessLink, webDriver.getCurrentUrl());
    }

    public void clickOnVerifyLink(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement messageContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("message-content")
                )
        );

        String fullMessageLink = messageContent.getAttribute("src");
        webDriver.get(fullMessageLink);

        WebElement linkElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p[3]/a")
                )
        );

        linkElement.click();
    }

    public void clickOnVerificationEmail(WebDriver webDriver) throws InterruptedException {
        Boolean isPresent = false;


        for(int refreshAttemptNumber = 1; refreshAttemptNumber < 5; refreshAttemptNumber++) {

            Thread.sleep(15000);
            isPresent = webDriver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[1]/div/div[2]/div/div[1]/div[1]")).size() > 0;

            if(isPresent) {
                break;
            }
            else {
                //Click refresh button
                webDriver.findElement(By.xpath("//*[@id=\"app\"]/div/nav/form/button")).click();
            }
        }

        assert isPresent : "Email is taking too long";

        webDriver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[1]/div/div[2]/div/div[1]/div[1]")).click();

        Thread.sleep(10000);


    }

    public void goToInboxKitten(WebDriver webDriver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.open('" + Constants.inboxKitten + "','_blank');");
        ArrayList<String> switchTabs= new ArrayList<String> (webDriver.getWindowHandles());
        webDriver.switchTo().window(switchTabs.get(1));
        Thread.sleep(10000);
    }

    public void changeEmailOnInboxKitten(WebDriver webDriver, String tempEmail) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

        WebElement emailField = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                By.id("email-input")
                        )
        );

        emailField.click();
        emailField.clear();
        emailField.sendKeys(tempEmail.substring(0, tempEmail.length() - 16));

        webDriver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div[2]/form/div[2]/input")).click();
    }
}
