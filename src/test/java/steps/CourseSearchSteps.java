package steps;

import com.google.inject.Inject;
import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.CourseCatalogPage;
import pages.CoursePage;

import java.util.List;
import java.util.Random;

public class CourseSearchSteps {


    @Inject
    private CourseCatalogPage catalogPage;
    @Inject
    private CoursePage coursePage;
    @Inject
    private TestContext testContext;

    private static final String COURSE_KEY = "selectedCourse";

    @Given("Я открываю каталог курсов")
    public void openCatalog() {
        catalogPage.open();
    }

    @When("Я устанавливаю курсор в строку поиска")
    public void focusSearchInput() {
        catalogPage.focusOnSearchInput();
    }

    @When("Я ввожу наименование случайного курса из списка:")
    public void enterRandomCourseNameFromList(DataTable table) {
        List<String> courses = table.asList();
        String selectedCourse = courses.get(new Random().nextInt(courses.size()));
        System.out.println(">>> Выбран курс: " + selectedCourse);
        catalogPage.enterSearchText(selectedCourse);
        testContext.getScenarioContext().set(COURSE_KEY, selectedCourse);
    }

    @And("Я дожидаюсь появления карточек курса")
    public void waitForCourseCards() {
        catalogPage.waitForCoursesToBeVisible();
    }

    @And("Кликаю по карточке курса")
    public void clickOnFoundCourseCard() {
        String selectedCourse = testContext.getScenarioContext().get(COURSE_KEY, String.class);
        if (selectedCourse == null || selectedCourse.isBlank()) {
            throw new IllegalStateException("selectedCourse не найден в ScenarioContext!");
        }
        System.out.println(">>> Кликаем по карточке курса: " + selectedCourse);
        catalogPage.clickOnCourseByName(selectedCourse);
    }

    @Then("Открыта верная карточка выбранного курса")
    public void verifyCoursePageOpened() {
        String selectedCourse = testContext.getScenarioContext().get(COURSE_KEY, String.class);
        Assertions.assertTrue(coursePage.getCourseTitle().contains(selectedCourse),
                "Название курса не совпадает");
    }
}