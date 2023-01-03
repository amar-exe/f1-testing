package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        webDriver.get(Config.baseUrl);

        acceptMarketingCookies();
        navigateToTeamsPage();
        int nrOfTeams=findNumberOfTeams();

        assertEquals(findNumberOfTeams(),10);

    }

    private void navigateToTeamsPage(){
        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[6]/a")).click();
    }
    private void acceptMarketingCookies(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();
    }
    private int findNumberOfTeams(){
        List<WebElement> teams = webDriver.findElements(By.className("listing-link"));
        return teams.size();
    }
}
