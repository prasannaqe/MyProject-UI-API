package com.xxx;

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

public class LoginTest {
    WebDriver driver;
    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com";

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void loginViaUI() throws InterruptedException {
        driver.get(baseUrl);

        // Enter credentials
        driver.findElement(By.id("email")).sendKeys("prasanna.dummy1@gmail.com");
        driver.findElement(By.id("password")).sendKeys("amazon123");
        driver.findElement(By.xpath("//button[contains(text(), 'Submit')]")).click();

        // Wait and check if the dashboard is loaded
        Thread.sleep(2000);
        WebElement contactHeader = driver.findElement(By.xpath("//h1[contains(text(), 'Contact List')]"));
        Assert.assertTrue(contactHeader.isDisplayed(), "Login Failed!");
    }
    @Test(priority = 2)
    public void loginViaAPI() {
        RestAssured.baseURI = baseUrl;

        String requestBody = "{ \"email\": \"prasanna.dummy1@gmail.com\", \"password\": \"amazon123\" }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users/login")
                .then()
                .extract()
                .response();
        System.out.println(response.toString());

        Assert.assertEquals(response.getStatusCode(), 200, "API Login Failed!");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
