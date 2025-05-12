package driver;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import utils.HighlightingListener;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Guice Provider для WebDriver с кэшированием на уровне потока.
 */
@Singleton
public class WebDriverProvider implements Provider<WebDriver> {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    @Override
    public WebDriver get() {
        if (tlDriver.get() == null) {
            // 1) читаем свойство (chrome по умолчанию)
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            WebDriver raw;

            // 2) выбираем и настраиваем нужный драйвер
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOpts = new ChromeOptions();
                    raw = new ChromeDriver(chromeOpts);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    raw = new FirefoxDriver();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            // 3) навешиваем на сырый драйвер ваш HighlightingListener
            WebDriver decorated = new EventFiringDecorator(new HighlightingListener())
                                        .decorate(raw);

            tlDriver.set(decorated);
        }
        return tlDriver.get();
    }

    /** Закрывает драйвер текущего потока и очищает ThreadLocal */
    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}