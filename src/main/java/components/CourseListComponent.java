package components;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Компонент для работы со списком курсов и меню.
 */
public class CourseListComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- вынесенные константы ---

    /** Подстрока href для всех карточек-курсов */
    private static final String LESSONS_HREF_SUBSTRING = "/lessons/";

    /** Базовый селектор всех карточек */
    private static final By COURSE_CARD_ROOTS = By.cssSelector("a[href*='" + LESSONS_HREF_SUBSTRING + "']");

    /** Шаблон XPath для поиска карточки по названию внутри <h6> */
    private static final String CARD_BY_NAME_XPATH_TEMPLATE =
        "//a[contains(@href, '%1$s') and normalize-space(string(.//h6))='%2$s']";

    /** Локатор кнопки "Обучение" в хедере */
    private static final By LEARNING_MENU_BUTTON = By.cssSelector("nav span[title='Обучение']");

    /** Локатор ссылок на категории внутри меню */
    private static final By CATEGORY_LINK_SELECTOR = By.cssSelector(
        "nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']"
    );

    // --- /константы ---

    private List<CourseCardComponent> allCards;

    @Inject
    public CourseListComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLearningMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(LEARNING_MENU_BUTTON))
            .click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CATEGORY_LINK_SELECTOR));
    }

    public List<WebElement> getSubMenuItems() {
        return driver.findElements(CATEGORY_LINK_SELECTOR);
    }

    public void waitForReady() {
        List<WebElement> roots = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(COURSE_CARD_ROOTS)
        );
        this.allCards = roots.stream()
            .map(root -> new CourseCardComponent(driver, root))
            .collect(Collectors.toList());
    }

    public List<CourseCardComponent> getAllCards() {
        return allCards;
    }

    public List<CourseCardComponent> getCardsWithDates() {
        return getAllCards().stream()
            .filter(c -> c.tryGetStartDate().isPresent())
            .collect(Collectors.toList());
    }

    public List<String> getAllTitles() {
        return getAllCards().stream()
            .map(CourseCardComponent::getTitle)
            .collect(Collectors.toList());
    }

    /**
     * Кликает по курсу с указанным названием.
     */
    public void clickByName(String name) {
        // собираем конкретный локатор из шаблона
        By locator = By.xpath(
            String.format(CARD_BY_NAME_XPATH_TEMPLATE, LESSONS_HREF_SUBSTRING, name)
        );

        // ждём, что элемент появится
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        // ждём, что он станет кликабельным и кликаем
        WebElement courseCard = wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        );
        courseCard.click();
    }
}
