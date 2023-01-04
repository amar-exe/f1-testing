package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SuccessfullyRegisteredTest {
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
    public void runSuccessfullyRegisterTest() throws InterruptedException {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToRegisterScreen(webDriver);

        String tempEmail = CommonMethods.generateEmail();

        CommonMethods.testAccountCreation(webDriver, tempEmail);
    }


}
