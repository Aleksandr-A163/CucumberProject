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
 * ��������� ��� ������ �� ������� ������ � ����.
 */
public class CourseListComponent {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // �������� ������ "��������" � ������
    private final By learningMenuButton = By.cssSelector("nav span[title='��������']");
    // �������� ������ �� ��������� ������ ����
    private final By categoryLinkSelector = By.cssSelector("nav a.sc-1pgqitk-0.dNitgt[href*='/categories/']");

    // �������� �������� �����
    private final By cardRoots = By.cssSelector("a.sc-zzdkm7-0");
    private List<CourseCardComponent> allCards;

    @Inject
    public CourseListComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * ��������� ���� "��������" � ���������� ��������� ���������.
     */
    public void openLearningMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(learningMenuButton))
            .click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryLinkSelector));
    }

    /**
     * ���������� �������� ������� (���������).
     */
    public List<WebElement> getSubMenuItems() {
        return driver.findElements(categoryLinkSelector);
    }

    /**
     * ��� ��������� �������� ����� �� �������� � ����������� �� � CourseCardComponent.
     */
    public void waitForReady() {
        List<WebElement> roots = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(cardRoots)
        );
        this.allCards = roots.stream()
            .map(root -> new CourseCardComponent(driver, root))
            .collect(Collectors.toList());
    }

    /**
     * ���������� ��� ���������� �������� ������.
     */
    public List<CourseCardComponent> getAllCards() {
        return allCards;
    }

    /**
     * ���������� ������ �������� � ����� ������.
     */
    public List<CourseCardComponent> getCardsWithDates() {
        return allCards.stream()
            .filter(c -> c.tryGetStartDate().isPresent())
            .collect(Collectors.toList());
    }

    /**
     * ���������� ��������� ���� ������.
     */
    public List<String> getAllTitles() {
        return allCards.stream()
            .map(CourseCardComponent::getTitle)
            .collect(Collectors.toList());
    }

    /**
     * ������� �� ����� � ��������� ���������.
     */
    public void clickByName(String name) {
        getAllCards().stream()
            .filter(c -> c.getTitle().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("���� �� ������: " + name))
            .click();
    }
}