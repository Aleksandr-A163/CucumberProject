package steps;

import com.google.inject.Inject;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.CourseCatalogPage;
import pages.CoursePage;

import java.time.LocalDate;
import java.util.List;

public class CourseDateSteps {

    @Inject private WebDriver driver;
    @Inject private CourseCatalogPage catalogPage;
    @Inject private CoursePage coursePage;

    private List<String> filteredTitles;
    private LocalDate currentFilterDate;


    @When("Я иду в каталог курсов и фильтрую их по дате {string}")
    public void filterByDate(String dateStr) {
        currentFilterDate = LocalDate.parse(dateStr);
        // Открываем каталог напрямую
        driver.get("https://otus.ru/catalog/courses");
        catalogPage.waitForCoursesToBeVisible();
        filteredTitles = catalogPage.getCourseTitlesByDate(currentFilterDate);
    }

    @Then("Все найденные курсы стартуют от {string}")
    public void verifyCourseDates(String dateStr) {
        LocalDate expected = LocalDate.parse(dateStr);
        Assertions.assertFalse(filteredTitles.isEmpty(), "Не найдено курсов для даты " + expected);
        for (String title : filteredTitles) {
            catalogPage.clickOnCourseByName(title);
            LocalDate actual = coursePage.getCourseStartDateJsoup(expected.getYear());
            Assertions.assertEquals(
                expected, actual,
                String.format("Курс '%s': ожидали %s, получили %s", title, expected, actual)
            );
            driver.navigate().back();
            catalogPage.waitForCoursesToBeVisible();
        }
    }
}