package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactPage {
    WebDriver driver;

    By addContactButton = By.id("add-contact");
    By firstNameField = By.id("firstName");
    By lastNameField = By.id("lastName");
    By emailField = By.id("email");
    By phoneField = By.id("phone");
    By saveButton = By.id("submit");
    By contactName = By.xpath("//td[contains(text(), 'John Doe')]");

    public ContactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addContact(String firstName, String lastName, String email, String phone) {
        driver.findElement(addContactButton).click();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(saveButton).click();
    }

    public boolean isContactAdded() {
        return driver.findElement(contactName).isDisplayed();
    }
}
