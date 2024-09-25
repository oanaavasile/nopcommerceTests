package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Notifications {

    protected WebDriver driver;
    public static final By popup = By.id("bar-notification");
    //    public static final By popupErrors = By.cssSelector(".bar-notification.error .content");
    public static final By popupErrors = By.cssSelector(".bar-notification.error > p");

    public Notifications(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getPopUpErrors(WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        List<WebElement> errors = driver.findElements(popupErrors);

        List<String> errorTexts = new ArrayList<>();

        for (WebElement error : errors) {
            errorTexts.add(error.getText());
        }
        return errorTexts;
    }


}
