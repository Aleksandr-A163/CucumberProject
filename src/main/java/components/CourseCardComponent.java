package components;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

/**
 * ���������-������ ��� �������� ����� � ����������� CSS-�����������.
 */
public class CourseCardComponent {
    private final WebElement root;
    private final WebDriver driver;

    // ��������� �� ������� SeleniumHomeWork
    private final By titleSelector    = By.cssSelector("h6 > div");
    private final By dateTextSelector = By.cssSelector("div[class*='sc-157icee-1'] > div");
    private final By categorySelector = By.cssSelector("p[class*='sc-1mes8t2-2']");

    public CourseCardComponent(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root   = root;
    }

    /** ��������� ����� */
    public String getTitle() {
        try {
            return root.findElement(titleSelector)
                       .getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /** �������� �������� ���� ������ */
    public Optional<LocalDate> tryGetStartDate() {
        try {
            String fullText = root.findElement(dateTextSelector)
                                  .getText().trim();
            String datePart = fullText.split(" � ")[0];
            DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("d MMMM, yyyy", new Locale("ru"));
            return Optional.of(LocalDate.parse(datePart, fmt));
        } catch (DateTimeParseException | NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /** ���� �� �������� � ��������� � �������� */
    public void click() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(root));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center', inline:'center'});", root
        );
        try {
            root.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", root);
        }
    }

    /** �������� HTML ���������� */
    public String getInnerHtml() {
        return root.getAttribute("innerHTML");
    }

    /** ��������� HTML ����� Jsoup */
    public Document getJsoupDocument() {
        return Jsoup.parse(getInnerHtml());
    }

    /** �������� WebElement �������� */
    public WebElement getRoot() {
        return root;
    }
}