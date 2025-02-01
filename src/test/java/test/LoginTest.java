package test;

import api.ApiClient;
import base.BaseTest;
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

public class LoginTest extends BaseTest {
    @Test(priority = 1)
    public void loginViaUI() throws InterruptedException {
        driver.get(Config.BASE_URL);
        LoginPage loginPage=new LoginPage(driver);
        loginPage.login(Config.EMAIL, Config.PASSWORD);
        Thread.sleep(2000);
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login Failed!");
    }
    @Test(priority = 2)
    public void loginViaAPI() {
        String token = ApiClient.getAuthToken();
        Assert.assertNotNull(token,"API Login failed");
    }
}
