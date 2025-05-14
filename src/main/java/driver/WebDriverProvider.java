package driver;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import utils.HighlightingListener;

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
                    // запускаем сразу в развернутом виде
                    chromeOpts.addArguments("--start-maximized");
                    raw = new ChromeDriver(chromeOpts);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOpts = new FirefoxOptions();
                    // задаём высокое разрешение или можно максимизировать ниже
                    firefoxOpts.addArguments("--width=1920", "--height=1080");
                    raw = new FirefoxDriver(firefoxOpts);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            // 3) навешиваем на сырой драйвер HighlightingListener
            WebDriver decorated = new EventFiringDecorator(new HighlightingListener())
                                        .decorate(raw);

            // 4) на всякий случай ещё и явно максимизируем окно
            try {
                decorated.manage().window().maximize();
            } catch (Exception ignore) {
                // в некоторых headless-сценариях может не сработать — пропускаем
            }

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