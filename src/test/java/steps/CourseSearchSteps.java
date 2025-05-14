package steps;

import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.CourseCatalogPage;
import pages.CoursePage;

import java.util.List;
import java.util.Random;

public class CourseSearchSteps {

    @Inject private WebDriver driver;
    @Inject private CourseCatalogPage catalogPage;
    @Inject private CoursePage coursePage;

    private String selectedCourse;

    @Given("Я открываю каталог курсов")
    public void openCatalog() {
        driver.get("https://otus.ru/catalog/courses");
        catalogPage.waitForCoursesToBeVisible();
    }

    @When("Я ввожу наименование курса в строку поиска")
    public void enterCourseNameInSearch() {
        catalogPage.enterSearchText(selectedCourse);
    }

    @When("Я выбираю случайный курс из списка:")
    public void selectRandomCourse(DataTable table) {
        List<String> courses = table.asList();
        // выбираем курс до того, как вводим его в поиск
        selectedCourse = courses.get(new Random().nextInt(courses.size()));
        catalogPage.clickOnCourseByName(selectedCourse);
    }

    @Then("Открывается страница выбранного курса")
    public void verifyCoursePage() {
        Assertions.assertTrue(
            coursePage.isCorrectCourseOpened(selectedCourse),
            "Открыт неверный курс. Ожидался: " + selectedCourse
        );
    }
}