package testBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.ConfigReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;

public class BaseClass {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger;

    public WebDriver getDriver() {
        return driver.get();
    }
    @BeforeClass
    public void setup() throws IOException {

        logger = LogManager.getLogger(this.getClass());
        logger.info("===== Test Execution Started =====");

        String browser = ConfigReader.getProperty("browser");
        String env = ConfigReader.getProperty("execution_env");
        String hubURL = ConfigReader.getProperty("hubURL");
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        try {

            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");

                if (headless) {
                    options.addArguments("--headless=new");
                }

                if (env.equalsIgnoreCase("grid")) {

                    driver.set(new RemoteWebDriver(new URL(hubURL), options));
                    logger.info("Running on Selenium Grid - Chrome");

                } else {

                    driver.set(new ChromeDriver(options));
                    logger.info("Running on Local Chrome");

                }

            } else if (browser.equalsIgnoreCase("edge")) {

                EdgeOptions options = new EdgeOptions();

                if (env.equalsIgnoreCase("grid")) {

                    driver.set(new RemoteWebDriver(new URL(hubURL), options));
                    logger.info("Running on Selenium Grid - Edge");

                } else {

                    driver.set(new EdgeDriver());
                    logger.info("Running on Local Edge");

                }

            } else if (browser.equalsIgnoreCase("firefox")) {

                FirefoxOptions options = new FirefoxOptions();

                if (env.equalsIgnoreCase("grid")) {

                    driver.set(new RemoteWebDriver(new URL(hubURL), options));
                    logger.info("Running on Selenium Grid - Firefox");

                } else {

                    driver.set(new FirefoxDriver());
                    logger.info("Running on Local Firefox");

                }
            }

        } catch (Exception e) {
            logger.error("Browser initialization failed", e);
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        getDriver().get(ConfigReader.getProperty("baseURL"));

        logger.info("Navigated to: " + ConfigReader.getProperty("baseURL"));

    }

    // ================= HARD ASSERT =================
    public void validatePageMessageHard(String actual, String expected, String pageName) {
        if (!actual.equals(expected)) {
            logger.error(pageName + " validation failed | Expected: " + expected + " | Actual: " + actual);
            throw new AssertionError(pageName + " validation failed");
        }
        logger.info(pageName + " validation passed");
    }

    // Attach driver to ITestResult
    @BeforeMethod(alwaysRun = true)
    public void attachDriverToTestResult(Method method, ITestResult result) {
        result.setAttribute("driver", driver);
    }

    @AfterClass
    public void tearDown() {

        if (getDriver() != null) {

            getDriver().quit(); // Close browser & Ends WebDriver session
            driver.remove();   // Remove thread referenc & Clears ThreadLocal memory

            logger.info("Browser closed successfully.");
        }
    }
}
