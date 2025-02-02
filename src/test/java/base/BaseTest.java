package base;

import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utils.ExtentManager;

public class BaseTest {
    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(this.getClass());
    protected ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentManager.initReport();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        logger.info("WebDriver initialized for " + browser);
    }

    @AfterMethod
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
        logger.info("Webdriver closed");
    }

    @AfterSuite
    public void tearDownReport() {
        ExtentManager.flushReport();
    }
}
