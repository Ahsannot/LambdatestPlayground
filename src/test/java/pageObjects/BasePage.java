package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    WebDriver driver;
    // Constructor gets driver from BaseClass
    public BasePage(WebDriver driver){
        this.driver = driver ;
        PageFactory.initElements(driver, this);
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void scrollIntoView(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public void waitAndScrollIfNeeded(WebElement element) {
        try {
            waitForVisibility(element);
        } catch (Exception e) {
            scrollIntoView(element);
            waitForVisibility(element);
        }
    }

    public void safeClick(WebElement element) {
        try {
            waitAndScrollIfNeeded(element);
            element.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    public void safeSendKeys(WebElement element, String value) {
        waitAndScrollIfNeeded(element);
        element.clear();
        element.sendKeys(value);
    }

}
