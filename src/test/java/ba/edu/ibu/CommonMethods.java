package ba.edu.ibu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonMethods {
    public static void acceptMarketingCookies(WebDriver webDriver){
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement acceptCookies = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"truste-consent-button\"]"))

        );
        acceptCookies.click();
    }
}
