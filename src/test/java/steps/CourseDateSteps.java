package steps;

import io.cucumber.guice.ScenarioScoped;
import com.google.inject.Inject;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import components.CourseListComponent;
import components.CourseCardComponent;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ScenarioScoped
public class CourseDateSteps {

    private final CourseListComponent courseList;
    private LocalDate expectedDate;
    private List<CourseCardComponent> filteredCards;

    @Inject
    public CourseDateSteps(CourseListComponent courseList) {
        this.courseList = courseList;
    }

    @When("Я фильтрую курсы по дате старта {string}")
    public void filterCoursesByStartDate(String date) {
        expectedDate = LocalDate.parse(date);
        filteredCards = courseList.getAllCourseCards().stream()
            .filter(card -> card.tryGetStartDate()
                .map(d -> !d.isBefore(expectedDate))
                .orElse(false))
            .sorted(Comparator.comparing(c -> c.tryGetStartDate().orElse(LocalDate.MAX)))
            .collect(Collectors.toList());
    }

    @Then("В консоль выведены названия и даты этих курсов")
    public void printCourseNamesAndDates() {
        filteredCards.forEach(card ->
            card.tryGetStartDate().ifPresent(date ->
                System.out.println("Курс: " + card.getTitle() + " — дата: " + date)
            )
        );
    }
}