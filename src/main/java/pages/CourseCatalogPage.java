package pages;

import com.google.inject.Inject;
import components.CourseListComponent;
import components.CourseCardComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object для страницы каталога курсов.
 */
public class CourseCatalogPage {

    private static final String URL = "https://otus.ru/catalog/courses";

    private final WebDriver driver;
    private final CourseListComponent courseList;

    @Inject
    public CourseCatalogPage(WebDriver driver,
                             CourseListComponent courseList) {
        this.driver = driver;
        this.courseList = courseList;
        PageFactory.initElements(driver, this);
    }

    public CourseCatalogPage open() {
        driver.get(URL);
        courseList.waitForReady();
        return this;
    }

    public boolean isOpened() {
        courseList.waitForReady();
        return !courseList.getAllCards().isEmpty();
    }

    public List<String> getAllCourseTitles() {
        return courseList.getAllTitles();
    }

    public void clickOnCourseByName(String name) {
        courseList.clickByName(name);
    }

    public List<CourseCardComponent> getAllCourseCardsWithDates() {
        return courseList.getCardsWithDates();
    }

    public LocalDate getEarliestCourseDate() {
        return getAllCourseCardsWithDates().stream()
            .map(c -> c.tryGetStartDate().orElseThrow())
            .min(LocalDate::compareTo)
            .orElseThrow();
    }

    public LocalDate getLatestCourseDate() {
        return getAllCourseCardsWithDates().stream()
            .map(c -> c.tryGetStartDate().orElseThrow())
            .max(LocalDate::compareTo)
            .orElseThrow();
    }

    public List<String> getCourseTitlesByDate(LocalDate date) {
        return getAllCourseCardsWithDates().stream()
            .filter(c -> c.tryGetStartDate().orElseThrow().equals(date))
            .map(CourseCardComponent::getTitle)
            .distinct()
            .collect(Collectors.toList());
    }

    public void waitForCoursesToBeVisible() {
        courseList.waitForReady();
    }

}