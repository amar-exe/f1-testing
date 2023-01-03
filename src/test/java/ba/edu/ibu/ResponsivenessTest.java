package ba.edu.ibu;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class ResponsivenessTest {
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
    void runResponsivenessTest(){
        webDriver.get(Config.baseUrl);
        Dimension dimension = new Dimension(414, 896);
        webDriver.manage().window().setSize(dimension);
        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            System.out.println("usoooooooooooooooo");
            BufferedImage bImage = ImageIO.read(screenshot);
            ImageIO.write(bImage, "jpg", new File(Config.path+"f1-testing\\export\\test.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
