package steps;

import com.google.inject.Inject;
import driver.WebDriverProvider;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class BrowserSteps {

    private WebDriver driver;

    @Inject
    private WebDriverProvider webDriverProvider;

    @Когда("Я открываю браузер {string}")
    public void openBrowser(String browser) {
        // Установить нужный драйвер (Chrome или Firefox)
        System.setProperty("browser", browser.toLowerCase());
        driver = webDriverProvider.get();
        // Открыть главную страницу Otus
        driver.get("https://otus.ru");
    }

    @Тогда("браузер запущен корректно")
    public void verifyBrowserLaunched() {
        // WebDriver не равен null
        Assertions.assertNotNull(driver, "WebDriver должен быть инициализирован");
        // Убедиться, что открыт URL Otus
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(
            currentUrl.startsWith("https://otus.ru"),
            "Ожидали URL https://otus.ru , но получили: " + currentUrl
        );
        // Дополнительно можно проверить, что заголовок страницы содержит слово «OTUS»
        String title = driver.getTitle();
        Assertions.assertTrue(
            title.toLowerCase().contains("otus"),
            "Ожидали, что title содержит 'otus', но было: " + title
        );
    }
}
