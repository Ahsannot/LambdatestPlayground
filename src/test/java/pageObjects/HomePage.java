package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver){
        super(driver);
    }

    //   ************************ LOCATORS ************************

    @FindBy(xpath = "//h3[normalize-space()='Top Trending Categories']")
    WebElement text_TopTrendingCategories;

    @FindBy(xpath = "//a[@role='button']//span[@class='title'][normalize-space()='My account']")
    WebElement link_My_account;

    @FindBy(xpath = "//span[normalize-space()='Register']")
    WebElement link_Register;

    //   ************************ ACTION METHODS ************************

    public String getConfirmationMessage(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmation = wait.until(ExpectedConditions.visibilityOf(text_TopTrendingCategories));
            return confirmation.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    public void hoverMyAccount(){
        Actions action = new Actions(driver);
        action.moveToElement(link_My_account).perform();
    }

    public RegistrationPage clicklinkRegister(){
        link_Register.click();
        return new RegistrationPage(driver);
    }

}
