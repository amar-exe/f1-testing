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

public class RemoveFromCartTest {
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
    public void removeFromCartTest() {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToTicketsScreen(webDriver);

        CommonMethods.acceptTicketsScreenCookies(webDriver);

        CommonMethods.hoverOverFirstGpCard(webDriver);

        CommonMethods.runAddToCart(webDriver);

        removeFromCart();

    }

    private void removeFromCart() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

        //Click checkout button
        webDriver.findElement(By.xpath("//*[@id=\"grandstand-content-wrapper\"]/div[6]/div/div[2]/a")).click();

        //Wait for shopping cart and delete button to load
        WebElement deleteBtn = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By
                                .xpath("//*[@id=\"funnel-wrapper\"]/div[4]/div/div[1]" +
                                        "/div[2]/div/div/div[1]/div[2]/div[2]/span[1]")
        ));
        deleteBtn.click();

        WebElement emptyCartHeader = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"funnel-wrapper\"]/div[4]/div/div[1]/div[2]/div/div/div[2]")
                )
        );
        assertEquals("Your shopping cart is empty.", emptyCartHeader.getText());
    }
}
