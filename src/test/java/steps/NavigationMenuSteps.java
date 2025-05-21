package steps;

import io.cucumber.guice.ScenarioScoped;
import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ScenarioScoped
public class NavigationMenuSteps {

    private final WebDriver driver;
    private final MainPage mainPage;
    private String selectedSlug;

    @Inject
    public NavigationMenuSteps(WebDriver driver, MainPage mainPage) {
        this.driver = driver;
        this.mainPage = mainPage;
    }

    @Given("Я нахожусь на главной странице")
    public void openHomePage() {
        mainPage.open();
    }

    @When("Я открываю меню «Обучение»")
    public void openLearningMenu() {
        mainPage.openLearningMenu();
    }

    @When("Я выбираю случайную категорию")
    public void selectRandomCategory() {
        selectedSlug = mainPage.clickRandomCategory();
    }

    @Then("URL содержит параметр categories выбранной категории")
    public void verifyUrlContainsCategory() {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("categories=" + selectedSlug),
            "Ожидали в URL параметр categories=" + selectedSlug + ", но получили: " + url);
    }
}