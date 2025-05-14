package steps;

import components.CourseCardComponent;
import components.CourseListComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import com.google.inject.Inject;
import context.TestContext;
import context.ScenarioContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDateSteps {

    private final CourseListComponent courseList;
    private final ScenarioContext scenarioContext;

    @Inject
    public CourseDateSteps(CourseListComponent courseList, TestContext testContext) {
        this.courseList = courseList;
        this.scenarioContext = testContext.getScenarioContext();
    }

    @When("Я фильтрую курсы по дате старта {string}")
    public void filterCoursesByStartDate(String date) {
        LocalDate expectedDate = LocalDate.parse(date);
        scenarioContext.set("expectedDate", expectedDate);

        courseList.waitForCourseCards();
        List<CourseCardComponent> allCards = courseList.getCardsWithDates();

        List<CourseCardComponent> filteredCards = allCards.stream()
            .filter(c -> c.tryGetStartDate()
                          .filter(d -> !d.isBefore(expectedDate))
                          .isPresent())
            .sorted(Comparator.comparing(c -> c.tryGetStartDate().orElse(LocalDate.MAX)))
            .collect(Collectors.toList());

        scenarioContext.set("filteredCards", filteredCards);
    }

    @Then("В консоль выведены названия и даты этих курсов")
    public void printCourseNamesAndDates() {
        LocalDate expectedDate = scenarioContext.get("expectedDate", LocalDate.class);
        Assertions.assertNotNull(expectedDate, "Операция фильтрации по дате не была выполнена, expectedDate == null");

        List<CourseCardComponent> filteredCards = scenarioContext.get("filteredCards", List.class);
        Assertions.assertNotNull(filteredCards, "filteredCards не были найдены в контексте");

        for (CourseCardComponent card : filteredCards) {
            card.tryGetStartDate().ifPresent(date -> {
                System.out.println("Курс: " + card.getTitle() + " — дата начала: " + date);
            });
        }
    }
}
