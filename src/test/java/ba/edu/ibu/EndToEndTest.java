package ba.edu.ibu;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static ba.edu.ibu.DriverStatsTest.checkDriverStats;
import static ba.edu.ibu.RegisterRegexTest.testPasswordField;
import static ba.edu.ibu.RemoveFromCartTest.removeFromCart;
import static ba.edu.ibu.TeamsCountTest.findNumberOfTeams;
import static ba.edu.ibu.TeamsCountTest.navigateToTeamsPage;
import static ba.edu.ibu.TimeCorrectnessTest.*;
import static ba.edu.ibu.VerifyAccountTest.*;
import static ba.edu.ibu.WhiteNavigationBarTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EndToEndTest {
    private static WebDriver webDriver;
    public static String tempEmail = "";
    public static String originalHandle = "";

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", Config.path+"chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        webDriver = new ChromeDriver(options);
        webDriver.get(Constants.baseUrl);
        tempEmail = CommonMethods.generateEmail();
        originalHandle = webDriver.getWindowHandle();
    }

    @AfterAll
    static void tearDown() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    void testRegisterRegex() throws InterruptedException {

        CommonMethods.acceptMarketingCookies(webDriver);

        CommonMethods.goToRegisterScreen(webDriver);

        CommonMethods.inputAllFieldsExceptPasswordOnRegisterScreen(webDriver, tempEmail);

        WebElement passwordField = webDriver.findElement(By.id("Password-input"));
        WebElement registerBtn = webDriver.findElement(By.xpath("//*[@id=\"registration-form\"]/div/div/div/div/div[2]/div[11]/div/div/div[1]/a"));
        registerBtn.click();
        WebElement minUppercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[1]"));
        WebElement minLowercase = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[2]"));
        WebElement minNumber = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[3]"));
        WebElement min8Char = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[4]"));
        WebElement minSpecial = webDriver.findElement(By.xpath("//*[@id=\"password-rule-container\"]/div[5]"));

        testPasswordField(minUppercase, minLowercase, minNumber, min8Char, minSpecial, passwordField, registerBtn);
        passwordField.clear();

    }

    @Test
    @Order(2)
    void testRegistration() throws InterruptedException {

        webDriver.get(Constants.baseUrl);

        CommonMethods.goToRegisterScreen(webDriver);

        CommonMethods.testAccountCreation(webDriver, tempEmail);

    }

    @Test
    @Order(3)
    void testAccountVerification() throws InterruptedException {

        webDriver.get(Constants.baseUrl);

        goToInboxKitten(webDriver);

        changeEmailOnInboxKitten(webDriver, tempEmail);

        clickOnVerificationEmail(webDriver);

        clickOnVerifyLink(webDriver);

        checkVerificationSuccess(webDriver);

        for(String handle : webDriver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                webDriver.switchTo().window(handle);
                webDriver.close();
            }
        }

        webDriver.switchTo().window(originalHandle);

    }

    @Test
    @Order(4)
    void testWhiteNavBar() {
        checkFiaLink(webDriver);
        checkFormulaLeaguesLinks(webDriver);
        checkStoresLinks(webDriver);
    }
    @Test
    @Order(5)
    void testRedNavBar() {
        webDriver.get(Constants.baseUrl);

        for(int elementNr = 1; elementNr <=8; elementNr++) {
            webDriver.findElement(By.xpath("//*[@id=\"primaryNav\"]/div/div[2]/ul/li["+elementNr+"]/a")).click();
            assertEquals(Constants.redNavigationTitles[elementNr-1], webDriver.getTitle());
        }
    }

    @Test
    @Order(6)
    void testTimeCorrectness() {
        webDriver.get(Constants.baseUrl);

        goToRaceSchedule(webDriver);
        checkSakhirTime(webDriver);
        checkLocalTime(webDriver);
    }

    @Test
    @Order(7)
    void testConstructorsCount() {

        navigateToTeamsPage(webDriver);

        assertEquals(Constants.nrOfF1Teams,findNumberOfTeams(webDriver));
    }

    @Test
    @Order(8)
    void testDriverInfo() {
        webDriver.navigate().to("https://www.formula1.com/en/results.html/2022/drivers.html");
        checkDriverStats(webDriver);
    }

    @Test
    @Order(9)
    void testAddingToCart() throws InterruptedException {
        webDriver.navigate().to(Constants.baseUrl);

        CommonMethods.goToTicketsScreen(webDriver);

        CommonMethods.acceptTicketsScreenCookies(webDriver);

        CommonMethods.hoverOverFirstGpCard(webDriver);

        Thread.sleep(2000);

        CommonMethods.runAddToCart(webDriver);
    }

    @Test
    @Order(10)
    void testRemovingFromCart() {
        removeFromCart(webDriver);
    }

    @Test
    @Order(11)
    void testChangingCurrency() throws InterruptedException {
        webDriver.navigate().to("https://tickets.formula1.com/en");

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
        System.out.println("Finished normal tests. Now opening python executable to run log in tests...");
        webDriver.quit();
    }

    @Test
    @Order(12)
    void testLoginFailed() throws IOException, InterruptedException {
        CommonMethods.clipboardFix();
        Runtime.getRuntime().exec("C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\fakeLogin.exe", null, new File("C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest"));
        File file = new File(
                "C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\output.txt");
        while(!file.exists()){
            Thread.sleep(5000);
            file=new File(
                    "C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\output.txt");
        }

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String text = br.readLine();
        br.close();
        CommonMethods.removeTempFile();
        assertEquals("PASSED",text);
    }

    @Test
    @Order(13)
    void testLoginSuccessful() throws IOException, InterruptedException {
        webDriver.quit();
        CommonMethods.clipboardFix();

        Runtime.getRuntime().exec("C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\goodLogin.exe", null, new File("C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest"));
        File file = new File(
                "C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\output.txt");
        while(!file.exists()){
            Thread.sleep(5000);
            file=new File(
                    "C:\\Users\\Amar\\Desktop\\Folders\\School\\V Semester\\System Verification, Validaiton and Testing\\Labs-GitHub\\f1-testing\\Formula1-Testing\\loginTest\\output.txt");
        }

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String text = br.readLine();
        br.close();
        CommonMethods.removeTempFile();
        assertEquals("PASSED",text);
    }
}
