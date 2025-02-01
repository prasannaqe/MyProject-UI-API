package test;

import api.ApiClient;
import base.BaseTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.Config;
import utils.ExtentManager;

public class LoginTest extends BaseTest {
    @Test(priority = 1)
    public void loginViaUI() throws InterruptedException {
        test = ExtentManager.createTest("UI Login Test");
        logger.info("Starting UI Login test");
        driver.get(Config.BASE_URL);
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login(Config.EMAIL, Config.PASSWORD);
        Thread.sleep(2000);
        boolean result = loginPage.isLoginSuccessful();
        Assert.assertTrue(result, "Login Failed!");
        if (result) {
            test.log(Status.PASS, "UI Login Test Passed");
        } else {
            test.log(Status.FAIL, "UI Login Test Failed");
        }

    }
    @Test(priority = 2)
    public void loginViaAPI() {
        test = ExtentManager.createTest("API Login Test");
        logger.info("Starting UI Login test");
        String token = ApiClient.getAuthToken();
        boolean result = (token != null);
        Assert.assertTrue(result, "API Login Failed!");

        if (result) {
            test.log(Status.PASS, "API Login Test Passed");
        } else {
            test.log(Status.FAIL, "API Login Test Failed");
        }
    }
}
