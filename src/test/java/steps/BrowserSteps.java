package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Capabilities;
import io.cucumber.guice.ScenarioScoped;
import com.google.inject.Inject;
import pages.MainPage;

@ScenarioScoped
public class BrowserSteps {

    private final WebDriver driver;
    private final MainPage mainPage;

    @Inject
    public BrowserSteps(WebDriver driver, MainPage mainPage) {
        this.driver = driver;
        this.mainPage = mainPage;
    }
    @Given("установлен браузер")
    public void setBrowser() {
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