package ba.edu.ibu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResponsivenessTest {
    private static WebDriver webDriver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", Config.path+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--hide-scrollbars");
        webDriver = new ChromeDriver(options);
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    void runResponsivenessTest() throws InterruptedException {
        webDriver.get(Constants.baseUrl);

        CommonMethods.acceptMarketingCookies(webDriver);
        //low definition tests
        resizeAndTakeSS(new Dimension(360, 640),"mobile_ld");
        resizeAndTakeSS(new Dimension(601, 962),"tablet_ld");
        resizeAndTakeSS(new Dimension(1024, 768),"desktop_ld");
        //high definition tests
        resizeAndTakeSS(new Dimension(414, 896),"mobile_hd");
        resizeAndTakeSS(new Dimension(1280, 800),"tablet_hd");
        resizeAndTakeSS(new Dimension(1920, 1080),"desktop_hd");
    }

    private void resizeAndTakeSS(Dimension dimension, String deviceName){
        webDriver.manage().window().setSize(dimension);
        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            BufferedImage bImage = ImageIO.read(screenshot);
            File outputFile = new File(Config.path+"f1-testing\\export\\"+deviceName+".png");
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
