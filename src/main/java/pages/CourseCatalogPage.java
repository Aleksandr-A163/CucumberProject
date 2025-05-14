package pages;

import com.google.inject.Inject;
import components.CourseListComponent;
import components.CourseCardComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class CourseCatalogPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final CourseListComponent courseList;

    private static final String URL = "https://otus.ru/catalog/courses";
    private static final By SEARCH_INPUT = By.cssSelector("input.sc-nrhq9g-0");
    private static final By CARD_ROOTS    = By.cssSelector("a[href*='/lessons/']");

    @Inject
    public CourseCatalogPage(WebDriver driver,
                             CourseListComponent courseList) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.courseList = courseList;
        PageFactory.initElements(driver, this);
    }

    /** Открывает страницу каталога и ждёт загрузки карточек */
    public CourseCatalogPage open() {
        driver.get(URL);
        courseList.waitForReady();
        return this;
    }

    /** Проверяет, что каталог загружен */
    public boolean isOpened() {
        courseList.waitForReady();
        return !courseList.getAllCards().isEmpty();
    }

    /** Возвращает все заголовки курсов */
    public List<String> getAllCourseTitles() {
        return courseList.getAllTitles();
    }

    /** Кликает по карточке курса с указанным названием */
    public void clickOnCourseByName(String name) {
        courseList.clickByName(name);
    }

    /** Возвращает компоненты карточек с датами старта */
    public List<CourseCardComponent> getAllCourseCardsWithDates() {
        return courseList.getCardsWithDates();
    }

    /** Находит самую раннюю дату старта */
    public LocalDate getEarliestCourseDate() {
        return getAllCourseCardsWithDates().stream()
            .map(c -> c.tryGetStartDate().orElseThrow())
            .min(LocalDate::compareTo)
            .orElseThrow();
    }

    /** Находит самую позднюю дату старта */
    public LocalDate getLatestCourseDate() {
        return getAllCourseCardsWithDates().stream()
            .map(c -> c.tryGetStartDate().orElseThrow())
            .max(LocalDate::compareTo)
            .orElseThrow();
    }

    /** Возвращает заголовки курсов с указанной датой */
    public List<String> getCourseTitlesByDate(LocalDate date) {
        return getAllCourseCardsWithDates().stream()
            .filter(c -> c.tryGetStartDate().orElseThrow().equals(date))
            .map(CourseCardComponent::getTitle)
            .distinct()
            .collect(Collectors.toList());
    }

    /** Вводит текст в строку поиска и ждёт фильтрации результатов */
    public void enterSearchText(String text) {
        WebElement input = wait.until(
            ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT)
        );
        input.clear();
        input.sendKeys(text);
        // ждём, пока отфильтруются карточки по новому селектору
        wait.until(
            ExpectedConditions.textToBePresentInElementLocated(CARD_ROOTS, text)
        );
    }

    /** Ждёт появления всех карточек курса через CourseListComponent */
    public void waitForCoursesToBeVisible() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CARD_ROOTS));
    }
}
