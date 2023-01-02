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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterRegexTest {
    private static WebDriver webDriver;
    private static String baseUrl;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver",
                Config.path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
        baseUrl = "https://formula1.com/";
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    void task1() throws InterruptedException {
        webDriver.get(baseUrl);
        webDriver.findElement(By.xpath("//*[@id=\"truste-consent-button\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"globalNav\"]/div/div[2]/div[1]/div/a[1]")).click();
        webDriver.findElement(By.xpath("/html/body/div[2]/header/div/div[2]/ul/li[2]/a")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement registerHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[1]/h2"))
        );
        assertEquals("CREATE ACCOUNT", registerHeader.getText());

        Select titleSelect = new Select(webDriver.findElement(By.id("Title-input")));
        titleSelect.selectByValue("Mr");

        webDriver.findElement(By.xpath("//*[@id=\"FirstName-input\"]")).sendKeys("testingName");
        webDriver.findElement(By.xpath("//*[@id=\"LastName-input\"]")).sendKeys("testingSurname");
        WebElement dateInput = webDriver.findElement(By.id("BirthDate-input"));
        dateInput.clear();
        dateInput.click();
        dateInput.sendKeys("02012000");

        Select countrySelect = new Select(webDriver.findElement(By.id("Country-input")));
        countrySelect.selectByValue("BIH");
        webDriver.findElement(By.xpath("//*[@id=\"Email-input\"]")).sendKeys("amar.fazlic@stu.ibu.edu.ba");
        Thread.sleep(15000);
    }
}
