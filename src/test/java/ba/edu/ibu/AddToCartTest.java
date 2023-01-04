package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddToCartTest {
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
    void runAddToCartTest(){
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToTicketsScreen(webDriver);

        CommonMethods.acceptTicketsScreenCookies(webDriver);

        hoverOverFirstGpCard();

        //selecting first availiable GrandPrix
        webDriver.findElement(By.xpath("//*[@id=\"calendar-content-wrapper\"]/div[2]/div/div[1]/div/div/div[2]/div/a")).click();
        //selecting first availiable Stands
        webDriver.findElement(By.xpath("//*[@id=\"event-content-wrapper\"]/div[3]/div/fieldset/div/div[1]/div/div/div/div[3]/a")).click();
        //confirming addition to cart
        webDriver.findElement(By.xpath("//*[@id=\"grandstand-content-wrapper\"]/div[2]/div/fieldset/div/div[1]/div[2]/div[2]/div[2]/button")).click();

        //checking footer cart state
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement bottomCart = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"grandstand-content-wrapper\"]/div[6]/div/div[2]/p[1]"))
        );
        assertEquals("1 Product",bottomCart.getText());

        //checking header cart state
        WebElement topCart = webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[3]/ul/li[3]/a/div/div[1]"));
        assertEquals("1",topCart.getText());

    }
    private void hoverOverFirstGpCard(){
        Actions builder = new Actions(webDriver);
        WebElement firstGpCard = webDriver.findElement(By.xpath("//*[@id=\"calendar-content-wrapper\"]/div[2]/div/div[1]"));
        Action hoverOverFirstGpCard = builder.moveToElement(firstGpCard).build();
        hoverOverFirstGpCard.perform();
    }
}
