package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private static WebDriver webDriver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver",
                Config.path+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    void successfulLoginTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(Constants.baseUrl);
        goToLoginScreen();

        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.name("Login"))
        );

        WebElement passwordField = webDriver.findElement(By.name("Password"));
        WebElement loginBtn = webDriver.findElement(By.
                xpath("//*[@id=\"loginform\"]/div[4]/button"));

        testLoginDetails(emailField, passwordField, loginBtn);
        inputLoginDetails(emailField, passwordField, loginBtn);

    }

    private void goToLoginScreen() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        CommonMethods.acceptMarketingCookies(webDriver);

        WebElement goToSignIn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"globalNav\"]/div/div[2]/div[1]/div/a[1]"))

        );
        goToSignIn.click();
    }

    public static void testLoginDetails(WebElement emailField,
                                        WebElement passwordField,
                                        WebElement loginBtn) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        //verifyEmailAndPasswordErrorLabels(emailField, passwordField, loginBtn);

        //Wrong email
        emailField.sendKeys(Constants.testAccountEmail + "a");
        passwordField.sendKeys(Constants.testAccountPass);
        loginBtn.click();
        WebElement errorLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"loginform\"]/div[1]/span"))
        );
        Thread.sleep(5000);
        assertTrue(errorLabel
                .getText()
                .contains("There was a problem with your password or email address. " +
                        "Please check your details and try again."));

        verifyEmailAndPasswordErrorLabels(emailField, passwordField, loginBtn);

        //Wrong password
        emailField.sendKeys(Constants.testAccountEmail);
        passwordField.sendKeys(Constants.testAccountPass + "a");
        errorLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"loginform\"]/div[1]/span"))
        );
        assertTrue( errorLabel
                .getText()
                .contains("There was a problem with your password or email address. " +
                        "Please check your details and try again."));

        verifyEmailAndPasswordErrorLabels(emailField, passwordField, loginBtn);
    }

    public static void verifyEmailAndPasswordErrorLabels(WebElement emailField,
                                                         WebElement passwordField,
                                                         WebElement loginBtn) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // Reset
        emailField.clear();
        passwordField.clear();
        loginBtn.click();

        //Email error label
        WebElement emailError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"loginform\"]/div[2]/span"))
        );

        assertTrue(
                emailError
                        .getText()
                        .equals("Please enter valid username/email address")
        );

        //Password error label
        WebElement passwordError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"loginform\"]/div[3]/span[1]"))
        );

        assertTrue(
                passwordError
                        .getText()
                        .equals("Password is not correct")
        );

        emailField.clear();
        passwordField.clear();
        loginBtn.click();
    }

    public static void inputLoginDetails(WebElement emailField,
                                         WebElement passwordField,
                                         WebElement loginBtn) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        emailField.clear();
        emailField.click();
        emailField.sendKeys(Constants.testAccountEmail);
        Thread.sleep(5000);

        passwordField.clear();
        passwordField.click();
        Thread.sleep(5000);
        passwordField.sendKeys(Constants.testAccountPass);
        Thread.sleep(10000);
        loginBtn.click();

        WebElement usernameBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"globalNav\"]/div/div[2]/div[1]/div/a[2]/span/span")
                )
        );

        assertTrue(usernameBox
                .getText()
                .contains(
                        "FormulaOne"
                ));
    }
}
