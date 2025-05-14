package driver;

import org.openqa.selenium.WebDriver;

public class BrowserFactory {

    public static WebDriver create() {
        String browserProperty = System.getProperty("browser");
        BrowserType browser = BrowserType.from(browserProperty);
        return browser.createDriver();
    }
}