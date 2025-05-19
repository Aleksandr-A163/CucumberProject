package steps;

import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import components.CourseCardComponent;
import pages.CourseCatalogPage;
import pages.CoursePage;
import utils.CourseInfo;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ScenarioScoped
public class PriceFilterSteps {
    private final CourseCatalogPage catalogPage;
    private final CoursePage coursePage;
    private final WebDriver driver;
    private final List<CourseInfo> courseInfos = new ArrayList<>();

    @Inject
    public PriceFilterSteps(CourseCatalogPage catalogPage,
                            CoursePage coursePage,
                            WebDriver driver) {
        this.catalogPage = catalogPage;
        this.coursePage = coursePage;
        this.driver = driver;
    }

    @Given("Я открываю раздел \"Подготовительные курсы\"")
    public void openPreparatory() {
        catalogPage.openPreparatory();
    }

    @When("Я собираю информацию о цене каждого курса")
    public void collectPrices() {
        courseInfos.clear();
        int index = 0;
        while (true) {
            List<CourseCardComponent> cards = catalogPage.getCourseList().getAllCourseCards();
            if (index >= cards.size()) {
                break;
            }
            CourseCardComponent card = cards.get(index);
            String title = card.getTitle();
            card.openCourse();
            int price = coursePage.getPrice();
            courseInfos.add(new CourseInfo(title, price));
            driver.get("https://otus.ru/catalog/courses?education_types=online");
            catalogPage.getCourseList().waitForCourseCards();
            index++;
        }
    }

    @Then("В консоль выведены самый дешёвый и самый дорогой курсы с их названиями и ценами")
    public void printExtremePrices() {
        assertNotNull(courseInfos, "Список цен курсов не инициализирован");
        assertFalse(courseInfos.isEmpty(), "Список цен курсов пуст");

        int minPrice = courseInfos.stream()
            .mapToInt(CourseInfo::price)
            .min()
            .orElseThrow();
        int maxPrice = courseInfos.stream()
            .mapToInt(CourseInfo::price)
            .max()
            .orElseThrow();

        List<CourseInfo> cheapestCourses = courseInfos.stream()
            .filter(ci -> ci.price() == minPrice)
            .collect(Collectors.toList());
        if (cheapestCourses.size() > 1) {
            System.out.println("Найдено несколько самых дешёвых курсов:");
            cheapestCourses.forEach(ci ->
                System.out.println(" - " + ci.title() + " — " + ci.price() + " ₽")
            );
        } else {
            CourseInfo ci = cheapestCourses.get(0);
            System.out.println(
                "Самый дешёвый курс: " + ci.title() + " — " + ci.price() + " ₽"
            );
        }

        List<CourseInfo> mostExpensiveCourses = courseInfos.stream()
            .filter(ci -> ci.price() == maxPrice)
            .collect(Collectors.toList());
        if (mostExpensiveCourses.size() > 1) {
            System.out.println("Найдено несколько самых дорогих курсов:");
            mostExpensiveCourses.forEach(ci ->
                System.out.println(" - " + ci.title() + " — " + ci.price() + " ₽")
            );
        } else {
            CourseInfo ci = mostExpensiveCourses.get(0);
            System.out.println(
                "Самый дорогой курс: " + ci.title() + " — " + ci.price() + " ₽"
            );
        }
    }
}
