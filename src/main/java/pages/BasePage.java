package pages;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected abstract String getPath(); // Только path, хост централизован

    public void open() {
        driver.get(getBaseUrl() + getPath());
    }

    protected String getBaseUrl() {
        return System.getProperty("host", "https://otus.ru");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
