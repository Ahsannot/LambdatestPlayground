package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage{

    public RegistrationPage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//h1[normalize-space()='Register Account']")
    WebElement text_RegisterAccount;

    //   ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOf(text_RegisterAccount));
            return confirmation.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }
}
