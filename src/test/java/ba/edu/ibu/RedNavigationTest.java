package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedNavigationTest {
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
    void runRedNavigationTest(){
        webDriver.get(Config.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        for(int elementNr = 1; elementNr <=8; elementNr++) {
           webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li["+elementNr+"]/a")).click();
           assertEquals(Constants.redNavigationTitles[elementNr-1], webDriver.getTitle());
        }
    }
   
}
