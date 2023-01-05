package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeCurrencyTest {
    private static WebDriver webDriver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", Config.path+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {webDriver.quit();}
    @Test
    void runChangeCurrencyTest() throws InterruptedException {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToTicketsScreen(webDriver);

        CommonMethods.acceptTicketsScreenCookies(webDriver);

        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[3]/ul/li[4]")).click();

        //opening dropdown selector
        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[3]/ul/li[4]/div/div[2]/div[2]/span/label/input")).click();

        //selecting bahreini dinar
        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[3]/ul/li[4]/div/div[2]/div[2]/span/label/ul/li[5]/span[2]")).click();
        //Confirming change
        webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[3]/ul/li[4]/div/a")).click();

        //wait before extracting data using JS
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        String content = (String) js.executeScript("return document.getElementsByClassName('lang-currency')[0].innerHTML");
        content=content.replaceAll("[^a-zA-Z\\-]","");

        assertEquals("EN-BD",content);
    }
}
