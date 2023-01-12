package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TeamsCountTest {
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
    void runTeamsCountTest(){
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);
        navigateToTeamsPage(webDriver);

        assertEquals(Constants.nrOfF1Teams,findNumberOfTeams(webDriver));

    }

    public static void navigateToTeamsPage(WebDriver webDriver) {
        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[6]/a")).click();
    }
   
    public static int findNumberOfTeams(WebDriver webDriver){
        List<WebElement> teams = webDriver.findElements(By.className("listing-link"));
        return teams.size();
    }
}
