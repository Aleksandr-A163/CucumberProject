package steps;

import com.google.inject.Inject;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationMenuSteps {

    @Inject private WebDriver driver;
    @Inject private MainPage mainPage;
    private String selectedSlug;

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