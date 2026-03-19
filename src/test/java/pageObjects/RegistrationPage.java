package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ************************ LOCATORS ************************
    @FindBy(xpath = "//h1[normalize-space()='Register Account']")
    WebElement text_RegisterAccount;

    @FindBy(id = "input-firstname")
    WebElement input_firstname;

    @FindBy(id = "input-lastname")
    WebElement input_lastname;

    @FindBy(id = "input-email")
    WebElement input_email;

    @FindBy(id = "input-telephone")
    WebElement input_telephone;

    @FindBy(id = "input-password")
    WebElement input_password;

    @FindBy(id = "input-confirm")
    WebElement input_confirm;

    @FindBy(id = "input-newsletter-yes")
    WebElement radio_subscribe_yes;

    @FindBy(id = "input-newsletter-no")
    WebElement radio_subscribe_no;

    @FindBy(id = "input-agree")
    WebElement checkbox_agree;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btn_Continue;

    // ************************ ACTION METHODS ************************

    // Get a confirmation message safely
    public String getConfirmationMessage() {
        try {
            waitAndScrollIfNeeded(text_RegisterAccount);
            return text_RegisterAccount.getText();
        } catch (Exception e) {
            System.out.println("Error fetching confirmation message: " + e.getMessage());
            return null;
        }
    }

    // Input fields with safe wait and scroll
    public void inputFirstName(String firstname) {
        safeSendKeys(input_firstname, firstname);
    }

    public void inputLastName(String lastname) {
        safeSendKeys(input_lastname, lastname);
    }

    public void inputEmail(String email) {
        safeSendKeys(input_email, email);
    }

    public void inputTelephone(String telephone) {
        safeSendKeys(input_telephone, telephone);
    }

    public void inputPassword(String password) {
        safeSendKeys(input_password, password);
    }

    public void inputConfirm(String confirm) {
        safeSendKeys(input_confirm, confirm);
    }

    // Set Subscribe with wait and scroll
    public void setSubscribe(boolean subscribe) {
        if (subscribe) {
            if (!radio_subscribe_yes.isSelected()) {
                safeClick(radio_subscribe_yes);
            }
        } else {
            if (!radio_subscribe_no.isSelected()) {
                safeClick(radio_subscribe_no);
            }
        }
    }

    // Set Privacy Policy with wait and scroll
    public void setPrivacyPolicy(boolean agree) {
        if (agree && !checkbox_agree.isSelected()) {
            safeClick(checkbox_agree);
        } else if (!agree && checkbox_agree.isSelected()) {
            safeClick(checkbox_agree);
        }
    }

    // Submit button with wait and scroll
    public void submitBtn() {
        safeClick(btn_Continue);
    }

}