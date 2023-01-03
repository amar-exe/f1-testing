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
        options.addArguments("--headless");
        options.addArguments("--hide-scrollbars");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    public void successfullyRegisterTest() {
        webDriver.get(Config.baseUrl);
        
    }
}