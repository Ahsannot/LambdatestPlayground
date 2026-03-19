package testCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;
import pageObjects.SuccessPage;
import testBase.BaseClass;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.RegisterDataProvider;

import java.time.Duration;

public class TC_002DDTRegistrationPageTest extends BaseClass {

    String excelPath = ConfigReader.getProperty("excel_User_Register_Path");
    String sheetName = ConfigReader.getProperty("register_sheet_name");

    @Test(dataProvider = "RegisterData", dataProviderClass = RegisterDataProvider.class)
    public void registrationPageDDTxlx(String firstName,
                                       String lastName,
                                       String email,
                                       String telephone,
                                       String password,
                                       String confirmPassword,
                                       String subscribeStr,
                                       String privacyPolicyStr,
                                       String expectedResult,
                                       int rowIndex) throws Exception {

        logger.info("===== Starting Registration DDT Test =====");

        ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
        SoftAssert softAssert = new SoftAssert();

        String actualResult;

        // Safe boolean parsing
        boolean subscribe = subscribeStr.equalsIgnoreCase("true") || subscribeStr.equalsIgnoreCase("yes");
        boolean privacyPolicy = privacyPolicyStr.equalsIgnoreCase("true") || privacyPolicyStr.equalsIgnoreCase("yes");

        try {
            // Home Page
            HomePage homePage = new HomePage(getDriver());
            homePage.hoverMyAccount();
            RegistrationPage registrationPage = homePage.clicklinkRegister();

            validatePageMessageSoft(registrationPage.getConfirmationMessage(),
                    "Register Account",
                    "RegistrationPage",
                    softAssert);

            // Fill form
            registrationPage.inputFirstName(firstName);
            registrationPage.inputLastName(lastName);
            registrationPage.inputEmail(email);
            registrationPage.inputTelephone(telephone);
            registrationPage.inputPassword(password);
            registrationPage.inputConfirm(confirmPassword);
            registrationPage.setSubscribe(subscribe);
            registrationPage.setPrivacyPolicy(privacyPolicy);

            // Submit form and get SuccessPage if it appears
            SuccessPage successPage = registrationPage.submitBtn();
            boolean isSuccess = (successPage != null); // true if registration succeeded
            //  Decide a result
            actualResult = isSuccess ? "Pass" : "Fail";

            logger.info("FINAL RESULT → Expected: " + expectedResult + " | Actual: " + actualResult);

            // Update Excel
            excel.setCellData(actualResult, rowIndex, 9);

            if (actualResult.equalsIgnoreCase(expectedResult)) {
                excel.setCellData("Test Passed", rowIndex, 10);
                logger.info("Test Result: PASSED");
            } else {
                excel.setCellData("Test Failed", rowIndex, 10);
                logger.error("Test Result: FAILED");

                softAssert.fail("Mismatch → Expected: " + expectedResult + " but got: " + actualResult);
            }

        } catch (Exception e) {

            logger.error("Unexpected Exception for user: " + email + " | " + e.getMessage());

            actualResult = "Fail";

            excel.setCellData(actualResult, rowIndex, 9);

            if (actualResult.equalsIgnoreCase(expectedResult)) {
                excel.setCellData("Test Passed", rowIndex, 10);
            } else {
                excel.setCellData("Test Failed", rowIndex, 10);
                softAssert.fail("Exception: " + e.getMessage());
            }

        } finally {

            excel.closeWorkbook();
            logger.info("Navigating back to base URL");
            getDriver().navigate().to(ConfigReader.getProperty("baseURL"));
        }

        softAssert.assertAll();
        logger.info("===== Finished Registration DDT Test =====");
    }
}