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

   // Селектор карточек курса: все ссылки на уроки/курсы
    private static final By COURSE_CARD_ROOTS = By.cssSelector("a[href*='/lessons/']");

    // Селектор кнопки "Обучение" в хедере (оставлен без изменений)
    private static final By LEARNING_MENU_BUTTON = By.cssSelector("nav span[title='Обучение']");
    // Селектор ссылок на категории внутри меню (оставлен без изменений)
    private static final By CATEGORY_LINK_SELECTOR = By.cssSelector("nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']");

    private List<CourseCardComponent> allCards;

    @Inject
    public CourseListComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Открывает меню "Обучение" и дожидается появления категорий.
     */
    public void openLearningMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(LEARNING_MENU_BUTTON))
            .click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CATEGORY_LINK_SELECTOR));
    }

    /**
     * Возвращает элементы подменю (категории).
     */
    public List<WebElement> getSubMenuItems() {
        return driver.findElements(CATEGORY_LINK_SELECTOR);
    }

    /**
     * Ждёт появления карточек курса на странице и оборачивает их в CourseCardComponent.
     */
    public void waitForReady() {
       List<WebElement> roots = wait.until(
           ExpectedConditions.visibilityOfAllElementsLocatedBy(COURSE_CARD_ROOTS)
       );
         this.allCards = roots.stream()
             .map(root -> new CourseCardComponent(driver, root))
             .collect(Collectors.toList());
    }

    /**
     * Возвращает все компоненты карточек курсов.
     */
    public List<CourseCardComponent> getAllCards() {
        return allCards;
    }

    /**
     * Возвращает список карточек с датой старта.
     */
    public List<CourseCardComponent> getCardsWithDates() {
        return getAllCards().stream()
            .filter(c -> c.tryGetStartDate().isPresent())
            .collect(Collectors.toList());
    }

    /**
     * Возвращает заголовки всех курсов.
     */
    public List<String> getAllTitles() {
        return getAllCards().stream()
            .map(CourseCardComponent::getTitle)
            .collect(Collectors.toList());
    }

    /**
     * Кликает по курсу с указанным названием.
     */
    public void clickByName(String name) {
        getAllCards().stream()
            .filter(c -> c.getTitle().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Курс не найден: " + name))
            .click();
    }
}