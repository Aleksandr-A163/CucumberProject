package steps;

import com.google.inject.Inject;
import components.CourseCardComponent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.CourseCatalogPage;
import pages.CoursePage;
import utils.CourseInfo;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        // Очищаем старые данные
        courseInfos.clear();

        int index = 0;
        while (true) {
            List<CourseCardComponent> cards =
                catalogPage.getCourseList().getAllCourseCards();
            if (index >= cards.size()) {
                break;
            }

            CourseCardComponent card = cards.get(index);
            String title = card.getTitle();
            card.openCourse();

            int price = coursePage.getPrice();
            courseInfos.add(new CourseInfo(title, price));

            // Явный возврат в каталог с фильтром онлайн-курсов
            driver.get("https://otus.ru/catalog/courses?education_types=online");
            catalogPage.getCourseList().waitForCourseCards();
            index++;
        }
    }

    @Then("В консоль выведены самый дешёвый и самый дорогой курсы с их названиями и ценами")
    public void printExtremePrices() {
        assertNotNull(courseInfos, "Список цен курсов не инициализирован");
        assertFalse(courseInfos.isEmpty(), "Список цен курсов пуст");

        CourseInfo cheapest = courseInfos.stream()
            .min(Comparator.comparingInt(CourseInfo::price))
            .orElseThrow(() -> new AssertionError("Не удалось найти самый дешёвый курс"));
        CourseInfo mostExpensive = courseInfos.stream()
            .max(Comparator.comparingInt(CourseInfo::price))
            .orElseThrow(() -> new AssertionError("Не удалось найти самый дорогой курс"));

        System.out.println("Самый дешёвый курс: " + cheapest.title() + " — " + cheapest.price() + " ₽");
        System.out.println("Самый дорогой курс: " + mostExpensive.title() + " — " + mostExpensive.price() + " ₽");
    }
}
