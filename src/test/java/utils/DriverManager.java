package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager(){}   //private constructor to prevent Instantiation

    public static WebDriver getDriver(){
        if(driver.get()==null){
            driver.set(BrowserFactory.getBrowser(System.getProperty("browser","chrome")));
        }
        return driver.get();
    }

    public static void quitDriver(){
        if(driver.get()!=null){
            driver.get().quit();
            driver.remove();
        }
    }
}
