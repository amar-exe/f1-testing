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

        acceptMarketingCookies();

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[1]/a")).click();
        assertEquals("Latest News", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[2]/a")).click();
        assertEquals("Video", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[3]/a")).click();
        assertEquals("F1 Schedule 2022 - Official Calendar of Grand Prix Races", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[4]/a")).click();
        assertEquals("2022 RACE RESULTS", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[5]/a")).click();
        assertEquals("F1 Drivers 2023 - Verstappen, Hamilton, Leclerc and more", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[6]/a")).click();
        assertEquals("F1 Racing Teams 2023 - Ferrari, Mercedes, Red Bull and more", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[7]/a")).click();
        assertEquals("F1 Games - Experience F1 Fantasy and Other Video Games", webDriver.getTitle());

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li[8]/a")).click();
        assertEquals("Live Timing", webDriver.getTitle());
    }
    private void acceptMarketingCookies(){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();
    }
}
