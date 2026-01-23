//package manager;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//import java.time.Duration;
//
//public class AppManager {
//    private WebDriver driver;
//
//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    @BeforeMethod
//    public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//    }
//
//    // (@BeforeMethod) setup --> (@Test) testName -->(@AfterMethod) tearDown
//
//    @AfterMethod(enabled = false)
//    public void tearDown() {
//        if (driver != null)
//            driver.quit();
//    }
//}
//package manager;
//
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
//import java.time.Duration;
//
//public class AppManager {
//
//    protected WebDriver driver;
//
//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    @BeforeMethod
//    public void setup() {
//        ChromeOptions options = new ChromeOptions();
//
//        // если у тебя нет нужды — можешь убрать
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--disable-notifications");
//        options.addArguments("--disable-popup-blocking");
//
//        driver = new ChromeDriver(options);
//
//        // ВАЖНО: вместо maximize() (он у тебя падал)
//        driver.manage().window().setSize(new Dimension(1920, 1080));
//
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//
//        // если у тебя есть baseUrl — вставь сюда свой сайт
//        driver.get("https://ilcarro.web.app/");
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
package manager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class ApplicationManager {

    protected WebDriver driver;

    // Поменяй URL если нужно (iLCarro / PhoneBook)
    private static final String BASE_URL = "https://telranedu.web.app/home";

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        // опционально, но обычно не мешает
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        // ВАЖНО: вместо maximize() (у тебя на нем падало)
        driver.manage().window().setSize(new Dimension(1920, 1080));

        // чистим сессию/куки, чтобы не "подтягивались запросы/логины"
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.get(BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
