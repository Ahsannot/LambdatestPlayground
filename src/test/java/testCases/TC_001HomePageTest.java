package testCases;

import org.testng.annotations.Test;
import pageObjects.HomePage;
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

    }
}
