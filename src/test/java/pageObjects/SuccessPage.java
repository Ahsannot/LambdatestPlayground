package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuccessPage extends BasePage{

    public SuccessPage(WebDriver driver){
        super(driver);
    }

    // ************************ LOCATORS ************************

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    public WebElement page_title;

    // ************************ ACTION METHODS ************************

}
