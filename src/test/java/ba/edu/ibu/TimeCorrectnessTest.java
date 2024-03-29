package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeCorrectnessTest {
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
    void runTimeCorrectnessTest(){
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);
        goToRaceSchedule(webDriver);
        checkSakhirTime(webDriver);
        checkLocalTime(webDriver);
    }
    public static void checkLocalTime(WebDriver webDriver){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement websiteLocal= wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"rolexClockYourTime\"]"))
        );
        String websiteLocalTime=websiteLocal.getText();

        String correctLocalTime=getLocalTime();
        assertEquals(correctLocalTime,websiteLocalTime);
    }
    public static String getLocalTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return(dtf.format(now));
    }
    public static void checkSakhirTime(WebDriver webDriver){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement websiteSakhir = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"rolexClockCityTime\"]"))

        );
        String websiteSakhirTime=websiteSakhir.getText();
        String correctSakhirTime=ApiGetter.getCurrentTimeInSakhir();
        assertEquals(correctSakhirTime,websiteSakhirTime);
    }
    public static void goToRaceSchedule(WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0,3500)");
    }
   
}
