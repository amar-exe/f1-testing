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

        CommonMethods.acceptMarketingCookies(webDriver);

        checkFiaLink();
        checkFormulaLeaguesLinks();
        checkStoresLinks();

    }

    public void checkStoresLinks() {
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

    public void checkFiaLink() {
        //FIA
        WebElement fiaWebsite = webDriver
                .findElement(By
                        .xpath("//*[@id=\"globalNav\"]/div/div[1]/a"));

        String fiaLink = fiaWebsite.getAttribute("href");
        assertEquals(Constants.whiteNavigationUrls[linkParser], fiaLink);
        linkParser++;
    }

    public void checkFormulaLeaguesLinks() {
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
