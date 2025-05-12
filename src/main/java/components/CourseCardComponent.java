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
 * Компонент-обёртка для карточки курса с корректными CSS-селекторами.
 */
public class CourseCardComponent {
    private final WebElement root;
    private final WebDriver driver;

    // Селекторы из проекта SeleniumHomeWork
    private final By titleSelector    = By.cssSelector("h6 > div");
    private final By dateTextSelector = By.cssSelector("div[class*='sc-157icee-1'] > div");
    private final By categorySelector = By.cssSelector("p[class*='sc-1mes8t2-2']");

    public CourseCardComponent(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root   = root;
    }

    /** Заголовок курса */
    public String getTitle() {
        try {
            return root.findElement(titleSelector)
                       .getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /** Пытается получить дату старта */
    public Optional<LocalDate> tryGetStartDate() {
        try {
            String fullText = root.findElement(dateTextSelector)
                                  .getText().trim();
            String datePart = fullText.split(" · ")[0];
            DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("d MMMM, yyyy", new Locale("ru"));
            return Optional.of(LocalDate.parse(datePart, fmt));
        } catch (DateTimeParseException | NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /** Клик по карточке с ожиданием и скроллом */
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

    /** Получить HTML компонента */
    public String getInnerHtml() {
        return root.getAttribute("innerHTML");
    }

    /** Разобрать HTML через Jsoup */
    public Document getJsoupDocument() {
        return Jsoup.parse(getInnerHtml());
    }

    /** Корневой WebElement карточки */
    public WebElement getRoot() {
        return root;
    }
}