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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.LoginPage;
import utils.Config;

public class AddContactTest extends BaseTest {
    private String token;

    @Test(priority = 1)
    public void addContactViaUI() throws InterruptedException {
        logger.info("Starting UI Contacts test");
        driver.get(Config.BASE_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Config.EMAIL, Config.PASSWORD);
        Thread.sleep(2000);
        ContactPage contactPage = new ContactPage(driver);
        contactPage.addContact("John", "Doe", "john.doe@example.com", "1234567890");
        Thread.sleep(2000);
        Assert.assertTrue(contactPage.isContactAdded(), "Contact Not Added!");
        logger.info("UI Contacts test passed");
    }

    @Test(priority = 2)
    public void addContactViaAPI() throws InterruptedException {
        logger.info("Starting API Contacts test");
        driver.get(Config.BASE_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Config.EMAIL, Config.PASSWORD);
        token = ApiClient.getAuthToken();
        Thread.sleep(2000);
        Response response = ApiClient.addContact("John", "Doe", "john.doe@example.com", "1234567890", token);
        Assert.assertEquals(response.getStatusCode(), 201, "API Contact Addition Failed!");
        logger.info("API Contacts test passed");
    }
}
