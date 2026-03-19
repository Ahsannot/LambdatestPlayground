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

    @FindBy(xpath = "//a[normalize-space()='Continue']")
    public WebElement btn_continue;

    // ************************ ACTION METHODS ************************

    // Get a confirmation message safely
    public String getConfirmationMessage() {
        try {
            waitAndScrollIfNeeded(page_title);
            return page_title.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return "";
        }
    }

    public void submitBtn() {
        safeClick(btn_continue);
    }
}
