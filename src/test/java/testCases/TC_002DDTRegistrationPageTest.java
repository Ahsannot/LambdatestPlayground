package testCases;

import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC_002DDTRegistrationPageTest extends BaseClass {

    @Test
   public void registrationPageDDTxlx(){

       logger.info("===== Starting Registration Page Test =====");

       HomePage homePage = new HomePage(getDriver());
       homePage.hoverMyAccount();
       RegistrationPage registrationPage = homePage.clicklinkRegister();

       validatePageMessageHard(
               registrationPage.getConfirmationMessage(),
               "Register Account",
               "RegistrationPage"
       );
       logger.info("===== Registration Page Test Passed =====");
   }

}
