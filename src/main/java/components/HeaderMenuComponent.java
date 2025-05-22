package components;

import io.cucumber.guice.ScenarioScoped;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * Компонент хедера с меню «Обучение».
 */
@ScenarioScoped
public class HeaderMenuComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** Кнопка «Обучение» */
    private final By learningMenuButton = By.cssSelector("nav span[title='Обучение']");
    /** Ссылки на категории */
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
    }

    /**
     * Кликает по случайной категории и возвращает slug.
     */
    public String clickRandomCategory() {
        List<WebElement> links = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryLinkSelector)
        );
        WebElement link = links.get(new Random().nextInt(links.size()));
        String href = link.getAttribute("href");
        String slug = href.substring(href.lastIndexOf('/') + 1);
        link.click();
        return slug;
    }
}
