package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.guice.ScenarioScoped;
import com.google.inject.Inject;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.*;


/**
 * Компонент для работы со списком курсов и меню.
 */
@ScenarioScoped
public class CourseListComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- селекторы ---

    /** Базовый селектор всех карточек */
    public static final By COURSE_CARD_ROOTS =
        By.xpath("//a[.//h6]");


    /** Селектор ссылок на категории внутри меню */
    private static final By CATEGORY_LINK_SELECTOR =
        By.cssSelector("nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']");

    /** Шаблон XPath для поиска карточки по названию */
    private static final String CARD_BY_NAME_XPATH_TEMPLATE =
        "//a[normalize-space(string(.//h6))='%s']";

    // --- /селекторы ---
    @Inject
    public CourseListComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Ждёт, пока карточки курсов станут видимыми на странице */
    public void waitForCourseCards() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(COURSE_CARD_ROOTS));
    }

    /**
     * Считывает **все** найденные на странице карточки
     */
    public List<CourseCardComponent> getAllCourseCards() {
        waitForCourseCards();
        return driver.findElements(COURSE_CARD_ROOTS).stream()
            // отбираем только те, у которых есть ненулевой размер и видимость
            .filter(e -> {
                try {
                    return e.isDisplayed()
                        && e.getSize().getWidth()  > 0
                        && e.getSize().getHeight() > 0;
                } catch (StaleElementReferenceException ex) {
                    return false;
                }
            })
            .map(e -> new CourseCardComponent(driver, e))
            .collect(Collectors.toList());
    }

    /**
     * Из всех карточек оставляет только те, где парсинг даты старта прошёл успешно
     */
    public List<CourseCardComponent> getCardsWithDates() {
        return getAllCourseCards().stream()
            .filter(c -> c.tryGetStartDate().isPresent())
            .collect(Collectors.toList());
    }

    /**
     * Кликает по карточке курса с точным совпадением заголовка
     * @param name точное наименование курса
     */
    public void clickByName(String name) {
        By locator = By.xpath(String.format(CARD_BY_NAME_XPATH_TEMPLATE, name));
        WebElement courseCard = wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        );
        courseCard.click();
    }
}
