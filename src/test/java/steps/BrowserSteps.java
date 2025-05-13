package steps;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Capabilities;

public class BrowserSteps {

    @Inject
    private WebDriver driver;

    @Given("установлен браузер")
    public void setBrowser() {
        // Ничего не делаем: браузер уже передан через -Pbrowser=<name>
        // Или System.setProperty("browser", ...) вызывали в другом месте
        String browser = System.getProperty("browser", "chrome");
        System.out.println(">>> Установлен browser=" + browser);
    }

    @When("я открываю главную страницу")
    public void openHomePage() {
        driver.get("https://otus.ru");
    }

    @Then("в логах отражается текущий браузер")
    public void verifyBrowser() {
        // Ожидаемое из system property
        String expected = System.getProperty("browser", "chrome").toLowerCase();

        // Реальный из capabilities
        HasCapabilities capDriver = (HasCapabilities) driver;
        Capabilities caps       = capDriver.getCapabilities();
        String actual           = caps.getBrowserName().toLowerCase();

        System.out.println("=== Запущен браузер: " + actual + " ===");

        // Сравниваем именно с переданным
        Assertions.assertEquals(
            expected,
            actual,
            "Ожидали browserName='" + expected + "', но получил: '" + actual + "'"
        );
    }
}