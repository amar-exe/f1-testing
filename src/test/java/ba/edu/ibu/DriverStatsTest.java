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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverStatsTest {
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
    public void testDriverStandings() throws InterruptedException {
        webDriver.get(Constants.baseUrl);
        CommonMethods.acceptMarketingCookies(webDriver);
        goToViewStandingsButton(webDriver);
        checkDriverStats(webDriver);
    }

    public static void checkDriverStats(WebDriver webDriver) {
        DriverModel driverModel = ApiGetter.getDrivers2022();

        for(int i = 1; i < 23; i++) {
            WebElement currentDriverPosition = webDriver
                    .findElement(By
                            .xpath("" +
                                    "/html/body/div[2]/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["
                                    + i +
                                    "]/td[2]"));

            WebElement currentDriverName = webDriver
                    .findElement(By
                            .xpath("/html/body/div[2]/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["
                            + i +
                            "]/td[3]/a/span[1]"));

            WebElement currentDriverSurname = webDriver
                    .findElement(By.
                            xpath("/html/body/div[2]/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["
                                    + i +
                                    "]/td[3]/a/span[2]"));

            WebElement currentDriverPoints = webDriver
                    .findElement(By
                            .xpath("/html/body/div[2]/main/article/div/div[2]/div[2]/div[2]/div/table/tbody/tr["
                                    + i +
                                    "]/td[6]"));
            DriverModel.StandingsList finalStandings = driverModel.StandingsTable.StandingsLists.get(driverModel.StandingsTable.StandingsLists.size() - 1);
            DriverModel.DriverStandings driverStandings = finalStandings.DriverStandings.get(i - 1);

            //Comparing position
            assertEquals(driverStandings.position,
                            currentDriverPosition.getText());

            //Comparing name
            if(!driverStandings.Driver.givenName.equals("Guanyu")) {
                assertEquals(driverStandings.Driver.givenName,
                        currentDriverName.getText());
            }

            //Comparing surname
            //TODO API returns Pérez and the website says Perez but Pérez is correct
            assertEquals(driverStandings.Driver.familyName,
                    currentDriverSurname.getText());

            //Check points
            assertEquals(driverStandings.points,
                    currentDriverPoints.getText());


        }
    }

    public static void goToViewStandingsButton(WebDriver webDriver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement viewFullStandingsBtn = webDriver
                .findElement(
                        By.xpath("/html/body/div[2]/main/div[7]/div/div[2]/div[1]/div/div/a"));
        js.executeScript("arguments[0].scrollIntoView(true)", viewFullStandingsBtn);
        js.executeScript("scrollBy(0, -150)");
        viewFullStandingsBtn.click();
    }
}
