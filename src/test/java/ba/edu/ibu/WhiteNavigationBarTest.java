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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhiteNavigationBarTest {
    private static WebDriver webDriver;
    int linkParser = 0;

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
    public void testWhiteNavigationBar() throws InterruptedException {

        webDriver.get(Config.baseUrl);

        String originalTabHandle = webDriver.getWindowHandle();

        acceptMarketingCookies();


        //FIA
        WebElement fiaWebsite = webDriver
                .findElement(By
                        .xpath("//*[@id=\"globalNav\"]/div/div[1]/a"));

        fiaWebsite.click();
        checkUrl(originalTabHandle);


        // F1 F2 F3
        for(int i = 1; i < 4; i++) {
            WebElement webElement = webDriver
                    .findElement(By
                            .xpath("//*[@id=\"globalNav\"]/div/div[3]/ul/li[" + i + "]/a"));

            webElement.click();
            moveSeleniumToOriginalTab(originalTabHandle);

            checkUrl(originalTabHandle);
        }
    }

    private void checkUrl(String originalTabHandle) throws InterruptedException {
        moveSeleniumToNewTab(originalTabHandle);
        assertEquals(Constants.whiteNavigationUrls[linkParser], webDriver.getCurrentUrl());
        linkParser++;
        webDriver.close();
        Thread.sleep(2000);
        moveSeleniumToOriginalTab(originalTabHandle);
        Thread.sleep(2000);


    }

    public static void moveSeleniumToNewTab(String originalTabHandle) {

        for (String handle: webDriver.getWindowHandles()) {
            if(!handle.equals(originalTabHandle)) {
                webDriver.switchTo().window(handle);
                System.out.println("handle:" + handle);
                System.out.println("website from selenium move: " + webDriver.getCurrentUrl());
                break;
            }
        }
    }

    public static void moveSeleniumToOriginalTab(String originalTabHandle) {
        webDriver.switchTo().window(originalTabHandle);
        System.out.println(webDriver.getCurrentUrl());
    }


    private void acceptMarketingCookies(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();
    }
}
