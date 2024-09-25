package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    public static WebDriver browserSetup() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-search-engine-choice-screen");

            WebDriver driver = new ChromeDriver(options);
            // or your preferred WebDriver initialization
            driver.manage().window().maximize();


        return driver;
    }

    public static void tearDown(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

}

