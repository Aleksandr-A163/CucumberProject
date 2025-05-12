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
 * ��������� ������ � ���� ���������.
 */
public class HeaderMenuComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** ������ ��������� � ������ */
    private final By learningMenuButton = By.cssSelector("nav span[title='��������']");
    /** ������ �� ��������� ������ ����������� ���� */
    private final By categoryLinkSelector =
        By.cssSelector("nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']");

    @Inject
    public HeaderMenuComponent(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** ��������� ���� ��������� */
    public void openLearningMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(learningMenuButton))
            .click();
        wait.until(ExpectedConditions
            .visibilityOfAllElementsLocatedBy(categoryLinkSelector));
    }

    /**
     * ������� �� ��������� ��������� � ���������� � slug (��������� ����� href).
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