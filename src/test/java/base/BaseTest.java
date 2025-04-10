package base;

import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.DriverManager;
import utils.ExtentManager;

import java.time.Duration;

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
        System.setProperty("browser", browser);
        driver= DriverManager.getDriver();
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

    public WebElement retryStaleException(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(driver1 ->{try{
                    WebElement element = driver.findElement(By.xpath(""));
                    element.isDisplayed();
                    return element;
                }catch (StaleElementReferenceException e){
                    return null;
                }});
    }
}
