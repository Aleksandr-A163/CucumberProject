package steps;

import com.google.inject.Inject;
import components.CourseCardComponent;
import io.cucumber.java.en.Given;
import pages.CourseCatalogPage;
import pages.CoursePage;
import utils.CourseInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PriceFilterSteps {
    private final CourseCatalogPage catalogPage;
    private final CoursePage coursePage;
    private final WebDriver driver;
    private List<CourseInfo> courseInfos;

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
        courseInfos = new ArrayList<>();

        int total = catalogPage.getCourseList().getAllCourseCards().size();
        for (int i = 0; i < total; i++) {
            // свежий список на каждой итерации
            List<CourseCardComponent> cards =
                catalogPage.getCourseList().getAllCourseCards();
            CourseCardComponent card = cards.get(i);

            String title = card.getTitle();
            card.openCourse();

            int price = coursePage.getPrice();
            courseInfos.add(new CourseInfo(title, price));

            driver.navigate().back();
            catalogPage.getCourseList().waitForCourseCards();
        }
    }

    @Then("В консоль выведены самый дешёвый и самый дорогой курсы с их названиями и ценами")
    public void printExtremePrices() {
        // находим min/max
        int minPrice = courseInfos.stream().mapToInt(CourseInfo::price).min().orElseThrow();
        int maxPrice = courseInfos.stream().mapToInt(CourseInfo::price).max().orElseThrow();

        CourseInfo cheapest = courseInfos.stream()
            .filter(ci -> ci.price() == minPrice)
            .findFirst().orElseThrow();
        CourseInfo mostExpensive = courseInfos.stream()
            .filter(ci -> ci.price() == maxPrice)
            .findFirst().orElseThrow();

        System.out.println("Самый дешёвый курс: " + cheapest);
        System.out.println("Самый дорогой курс: " + mostExpensive);
    }
}
