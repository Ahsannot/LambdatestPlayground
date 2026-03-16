package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
onStart()        → Initialize report
onTestStart()   → Create test entry
onTestSuccess() → Mark PASS
onTestFailure() → Mark FAIL + screenshot
onFinish()      → Flush report + Email sender

 */

    public class ExtentReportListener implements ITestListener {

        public static ExtentReports extentReports;
        public static ExtentTest test;
        public ExtentSparkReporter sparkReporter;

        // ITestContext context gives information about the current test run (suite name, test name, etc.)
        // It is called when the test suite starts
        public void onStart(ITestContext context) {

            // Prints the test suite name to the console
            System.out.println("onStart - Test Suite started: " + context.getName());

            // Generates a unique timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Create a folder named "reports" if it doesn't already exist
            File folder = new File("reports");
            folder.mkdirs(); // creates the folder (and parent folders if needed)

            // Final report file name / report file path
            String reportPath = "reports/SparkReport_" + timestamp + ".html";

            // Initializes Extent Spark Reporter
            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("Lambdatest Automation Report");
            sparkReporter.config().setDocumentTitle("Lambdatest Test Report");

            // Create ExtentReports instance
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }

        public void onTestStart(ITestResult result) {

            // Get class and method name
            String className = result.getTestClass().getName();
            String methodName = result.getMethod().getMethodName();

            //Create a test entry in Extent Report
            test = extentReports.createTest(className + " : " + methodName);
            test.info("Test Started: " + methodName + " in class " + className);
        }

        // ITestResult result contains details about the test that just ran
        public void onTestSuccess(ITestResult result) {

            // Get class and method name
            String className = result.getTestClass().getName();
            String methodName = result.getMethod().getMethodName();

            System.out.println("onTestSuccess - Passed Test: " + className + "." + methodName);
            // Mark test as PASSED in Extent Report
            test.pass("Test Passed: " + methodName + " in class " + className);
        }


        public void onTestFailure(ITestResult result) {

            // Get class and method name
            String className = result.getTestClass().getName();
            String methodName = result.getMethod().getMethodName();

            System.out.println("onTestFailure - Test Failed: " + className + "." + methodName);
            // Mark test as FAILED in Extent Report
            test.fail("Test Failed: " + methodName + " in class " + className);

            // Log the exception (root cause)
            test.fail(result.getThrowable());

            WebDriver driver = getDriverFromResult(result);

            // Take screenshot (only if driver exists)
            if (driver != null) {
                try {
                    // Capture screenshot
                    String screenshotpath = ScreenshotUtil.captureScreenshot(driver, methodName);
                    // Validate screenshot path
                    if (screenshotpath != null && new File(screenshotpath).exists()) {
                        // Attach screenshot to Extent Report
                        test.fail("Screenshot of failure:",
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
                    } else {
                        test.info("Screenshot path invalid or file does not exist.");
                    }
                } catch (Exception e) {
                    test.info("Could not attach screenshot: " + e.getMessage());
                }

            }

        }

        public void onTestSkipped(ITestResult result) {

            String className = result.getTestClass().getName();
            String methodName = result.getMethod().getMethodName();

            System.out.println("onTestSkipped - Skipped Test: " + className + "." + methodName);
            test.skip("Test Skipped: " + methodName + " in class " + className);
        }

        // Writes all buffered test data to the HTML report
        //Finalizes:Test results,Logs,Screenshots,Metadata
        //If you forget this:Report may be empty,Missing logs/screenshots,HTML file may not be created

        public void onFinish(ITestContext context) {

            System.out.println("onFinish - Test Suite finished: " + context.getName());
            // Write everything to HTML file
            extentReports.flush();

            // Get final report file path
            String reportPath = sparkReporter.getFile().getAbsolutePath();

            if (new File(reportPath).exists()) {

                System.out.println("Report exists at: " + reportPath);

                // Send report via email
                EmailReportSender.sendReport("hafizgee07@gmail.com", reportPath);

                System.out.println("Report Sent to hafizgee07@gmail.com");
            } else {

                System.out.println("Report file does not exist at path: " + reportPath);
            }
        }


        // Accepts the current test’s ITestResult, Returns: WebDriver
        public WebDriver getDriverFromResult(ITestResult result) {

            // Retrieve the stored attribute Fetches the object saved earlier using:
            // result.setAttribute("driver", driver);
            Object driverObj = result.getAttribute("driver");

            // Verifies the object is actually a WebDriver
            if (driverObj instanceof WebDriver) {
                // Returns the active browser driver
                return (WebDriver) driverObj;
            } else {
                System.out.println("WebDriver not found in ITestResult attributes.");
                return null;
            }
        }

    }

