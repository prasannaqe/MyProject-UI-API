package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    By emailField = By.id("email");
    By passwordField = By.id("password");
    By submitButton = By.xpath("//button[contains(text(), 'Submit')]");
    By contactHeader = By.xpath("//h1[contains(text(), 'Contact List')]");
    public LoginPage(WebDriver driver){
        this.driver= driver;
    }

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean isLoginSuccessful() {
        return driver.findElement(contactHeader).isDisplayed();
    }
}
