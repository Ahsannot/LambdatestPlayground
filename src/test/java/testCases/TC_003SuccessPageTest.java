package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SuccessPage;
import testBase.BaseClass;

public class TC_003SuccessPageTest extends BaseClass {

    @Test
    public void accountSuccess(){

        SuccessPage successPage = new SuccessPage(getDriver());
        String successMsg = successPage.getConfirmationMessage();
        Assert.assertEquals(successMsg,"Your Account Has Been Created!","User not Register");

        successPage.submitBtn();
    }
}
