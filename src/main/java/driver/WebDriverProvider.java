package driver;

import com.google.inject.Provider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import utils.HighlightingListener;

public class WebDriverProvider implements Provider<WebDriver> {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @Override
    public WebDriver get() {
        if (driverThreadLocal.get() == null) {
            WebDriver baseDriver = BrowserFactory.create();
            WebDriver decorated = new EventFiringDecorator<>(new HighlightingListener()).decorate(baseDriver);
            driverThreadLocal.set(decorated);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}