package steps;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;

public class BrowserSteps {

    @Inject
    private WebDriver driver;

    @Когда("Я открываю браузер {string}")
    public void openBrowser(String browser) {
        System.setProperty("browser", browser.toLowerCase());
        // провайдер уже сам создаст и вернёт экземпляр
        driver.get("https://otus.ru");
    }

    @Тогда("браузер запущен корректно")
    public void verifyBrowserLaunched() {
        Assertions.assertNotNull(driver, "WebDriver должен быть инициализирован");
        Assertions.assertTrue(
            driver.getCurrentUrl().startsWith("https://otus.ru"),
            "Ожидали URL Otus"
        );
    }
}