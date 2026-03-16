package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    // Logger for this utility class
    public static Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    /**
     * Captures screenshot and saves it in /screenshots folder
     *
     * @param driver         WebDriver instance
     * @param screenshotName Name to identify screenshot
     * @return Absolute path of saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {

        logger = LogManager.getLogger(ScreenshotUtil.class);

        try {
            // Capture screenshot as a temporary file
            // File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Step 1: Turn the driver into a screenshot-taking object
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Step 2: Take the screenshot and store it as a file
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

            // Prepare destination folder and filename
            String dir = System.getProperty("user.dir") + "/screenshots/";

            // Checks if the directory exist if not will create it
            Files.createDirectories(Paths.get(dir));

            String path = dir + screenshotName + "_" + timestamp + ".png";

            // Copy screenshot to destination
            Files.copy(source.toPath(), Paths.get(path));

            // Log and print confirmation
            logger.info("Screenshot saved to: {}", path);

            return path;

        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }
}
