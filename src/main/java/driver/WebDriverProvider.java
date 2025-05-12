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
 * Guice Provider ��� WebDriver � ������������ �� ������ ������.
 */
@Singleton
public class WebDriverProvider implements Provider<WebDriver> {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    @Override
    public WebDriver get() {
        if (tlDriver.get() == null) {
            // 1) ������ �������� (chrome �� ���������)
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            WebDriver raw;

            // 2) �������� � ����������� ������ �������
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

            // 3) ���������� �� ����� ������� ��� HighlightingListener
            WebDriver decorated = new EventFiringDecorator(new HighlightingListener())
                                        .decorate(raw);

            tlDriver.set(decorated);
        }
        return tlDriver.get();
    }

    /** ��������� ������� �������� ������ � ������� ThreadLocal */
    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}