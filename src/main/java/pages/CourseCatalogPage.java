package pages;

import com.google.inject.Inject;
import components.CourseCardComponent;
import components.CourseListComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CourseCatalogPage extends BasePage {

    private final CourseListComponent courseList;

    private static final By SEARCH_INPUT = By.cssSelector("input[type='search']");

    @Inject
    public CourseCatalogPage(WebDriver driver,
                             CourseListComponent courseList) {
        super(driver);
        this.courseList = courseList;
        PageFactory.initElements(driver, this);
    }

    @Override
    protected String getPath() {
        return "/catalog/courses";
    }

    /** Открывает страницу каталога и ждёт появления карточек */

    public void open() {
        super.open();
        courseList.waitForCourseCards();
    }


    /** Кликает по карточке курса с указанным названием */
    public void clickOnCourseByName(String name) {
        courseList.clickByName(name);
    }

    /** Возвращает все карточки с успешно распознанной датой старта */
    public List<CourseCardComponent> getAllCourseCardsWithDates() {
        return courseList.getCardsWithDates();
    }

    /** Возвращает заголовки курсов, стартующих не раньше указанной даты */
    public List<String> getCourseTitlesByDate(LocalDate date) {
    return getAllCourseCardsWithDates().stream()
        .filter(card -> card.startsOnOrAfter(date))
        .map(CourseCardComponent::getTitle)
        .distinct()
        .collect(Collectors.toList());
    }

    /** Явно дождаться загрузки карточек */
    public void waitForCoursesToBeVisible() {
        courseList.waitForCourseCards();
    }

    /** Фокус в поле поиска */
    public void focusOnSearchInput() {
        WebElement input = driver.findElement(SEARCH_INPUT);
        input.click();
    }

    /** Ввод текста в поле поиска */
    public void enterSearchText(String text) {
        WebElement input = driver.findElement(SEARCH_INPUT);
        input.clear();
        input.sendKeys(text);
    }
}
