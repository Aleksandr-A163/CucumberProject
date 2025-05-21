package steps;

import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.CourseCatalogPage;
import pages.CoursePage;

import java.util.List;
import java.util.Random;

@ScenarioScoped
public class CourseSearchSteps {

    private final CourseCatalogPage catalogPage;
    private final CoursePage coursePage;
    private String selectedCourse;

    @Inject
    public CourseSearchSteps(CourseCatalogPage catalogPage, CoursePage coursePage) {
        this.catalogPage = catalogPage;
        this.coursePage = coursePage;
    }

    @Given("Я открываю каталог курсов")
    public void openCatalog() {
        catalogPage.open();
    }

    @And("Я устанавливаю курсор в строку поиска")
    public void focusSearchInput() {
        catalogPage.focusOnSearchInput();
    }

    @When("Я ввожу наименование случайного курса из списка:")
    public void enterRandomCourseNameFromList(DataTable table) {
        List<String> courses = table.asList();
        selectedCourse = courses.get(new Random().nextInt(courses.size()));
        catalogPage.enterSearchText(selectedCourse);
    }

    @And("Я дожидаюсь появления карточек курса")
    public void waitForCourseCards() {
        catalogPage.waitForCoursesToBeVisible();
    }

    @And("Кликаю по карточке курса")
    public void clickOnFoundCourseCard() {
        Assertions.assertNotNull(selectedCourse);
        catalogPage.clickOnCourseByName(selectedCourse);
    }

    @Then("Открыта верная карточка выбранного курса")
    public void verifyCoursePageOpened() {
        Assertions.assertTrue(
            coursePage.getCourseTitle().contains(selectedCourse)
        );
    }
}