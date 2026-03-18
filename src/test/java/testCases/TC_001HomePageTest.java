package testCases;

import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import testBase.BaseClass;

public class TC_001HomePageTest extends BaseClass {

    @Test
    public void homePage(){

        logger.info("===== Starting Home Page Test =====");

        HomePage homePage = new HomePage(getDriver());

        validatePageMessageHard(
                homePage.getConfirmationMessage(),
                "TOP TRENDING CATEGORIES",
                "HomePage"
        );
        logger.info("===== Home Page Test Passed =====");

        homePage.hoverMyAccount();
        logger.info("===== Hover over My Account =====");

        RegistrationPage registrationPage = homePage.clicklinkRegister();
        logger.info("===== User redirected to Registration Page =====");
    }
}
