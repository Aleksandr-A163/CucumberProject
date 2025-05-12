package components;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * Компонент хедера с меню «Обучение».
 */
public class HeaderMenuComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Кнопка «Обучение» в хедере */
    private final By learningMenuButton = By.cssSelector("nav span[title='Обучение']");
    /** Ссылки на категории внутри выпадающего меню */
    private final By categoryLinkSelector =
        By.cssSelector("nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']");

    @Inject
    public HeaderMenuComponent(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Открывает меню «Обучение» */
    public void openLearningMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(learningMenuButton))
            .click();
        wait.until(ExpectedConditions
            .visibilityOfAllElementsLocatedBy(categoryLinkSelector));
    }

    /**
     * Кликает по случайной категории и возвращает её slug (последнюю часть href).
     */
    public String clickRandomCategory() {
        List<WebElement> links =
            wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(categoryLinkSelector));
        int idx = new Random().nextInt(links.size());
        WebElement link = links.get(idx);

        String href = link.getAttribute("href");
        String slug = href.substring(href.lastIndexOf('/') + 1);

        link.click();
        return slug;
    }
}