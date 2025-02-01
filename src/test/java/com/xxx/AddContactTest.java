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
public class AddContactTest {
    WebDriver driver;
    String baseUrl = "https://thinking-tester-contact-list.herokuapp.com";

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        // Login first
        driver.findElement(By.id("email")).sendKeys("prasanna.dummy1@gmail.com");
        driver.findElement(By.id("password")).sendKeys("amazon123");
        driver.findElement(By.xpath("//button[contains(text(), 'Submit')]")).click();
    }

    @Test(priority = 1)
    public void addContactViaUI() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(text(), 'Add a New Contact')]")).click();

        // Fill contact details
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
        driver.findElement(By.id("phone")).sendKeys("1234567890");

        driver.findElement(By.id("submit")).click();

        // Verify contact is added
        Thread.sleep(2000);
        WebElement contactName = driver.findElement(By.xpath("//td[contains(text(), 'John Doe')]"));
        Assert.assertTrue(contactName.isDisplayed(), "Contact Not Added!");
    }

    @Test(priority = 2)
    public void addContactViaAPI() {
        RestAssured.baseURI = baseUrl;

        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phone\": \"1234567890\" }";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NzlkOTNjZWEwYTNjZjAwMTMzMWQ5MGUiLCJpYXQiOjE3Mzg0Mjk3MjN9.7DQk_TfNFoTQcozfAcIdRYyYUJxN1_tTXvSF4iz-u3A") // Replace with valid token
                .body(requestBody)
                .when()
                .post("/contacts")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201, "API Contact Addition Failed!");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
