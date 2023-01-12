package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhiteNavigationBarTest {
    private static WebDriver webDriver;
    static int linkParser = 0;

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
    void runWhiteNavigationBarTest() throws InterruptedException {

        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        checkFiaLink(webDriver);
        checkFormulaLeaguesLinks(webDriver);
        checkStoresLinks(webDriver);

    }

    public static void checkStoresLinks(WebDriver webDriver) {
        //Stores and shops
        for(int elementNr = 2; elementNr < 7; elementNr++) {
            WebElement webElement = webDriver
                    .findElement(By
                            .xpath("//*[@id=\"globalNav\"]/div/div[2]/div[2]/ul/li["
                            + elementNr +
                            "]/a"));
            String webElementLink = webElement.getAttribute("href");
            assertEquals(Constants.whiteNavigationUrls[linkParser], webElementLink);
            linkParser++;
        }
    }

    public static void checkFiaLink(WebDriver webDriver) {
        //FIA
        WebElement fiaWebsite = webDriver
                .findElement(By
                        .xpath("//*[@id=\"globalNav\"]/div/div[1]/a"));

        String fiaLink = fiaWebsite.getAttribute("href");
        assertEquals(Constants.whiteNavigationUrls[linkParser], fiaLink);
        linkParser++;
    }

    public static void checkFormulaLeaguesLinks(WebDriver webDriver) {
        // F1 F2 F3
        //TODO F2 and F3 websites is https secure but the transfer link is http
        for(int elementNr = 1; elementNr < 4; elementNr++) {
            WebElement webElement = webDriver
                    .findElement(By
                            .xpath("//*[@id=\"globalNav\"]/div/div[3]/ul/li[" + elementNr + "]/a"));

            String webElementLink = webElement.getAttribute("href");
            assertEquals(Constants.whiteNavigationUrls[linkParser], webElementLink);
            linkParser++;
        }
    }


   
}
