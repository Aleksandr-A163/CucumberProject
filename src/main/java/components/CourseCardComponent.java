package components;

import com.google.inject.Inject;
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
    private final WebDriver driver;
    private final WebElement root;
    private final WebDriverWait wait;

    /** Селектор блока с датой курса */
    private static final By DATE_TEXT_SELECTOR = By.cssSelector("h6 + div > div");
    // локатор заголовка внутри карточки
    private static final By TITLE_LOCATOR = By.cssSelector("h6");

    @Inject
    public CourseCardComponent(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root = root;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Заголовок курса через Jsoup для надёжного парсинга
     */
    public String getTitle() {
        try {
            Document doc = getJsoupDocument();
            return doc.select("h6 > div").text().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Пытается получить дату старта в виде LocalDate
     */
    public Optional<LocalDate> tryGetStartDate() {
        try {
            String fullText = root.findElement(DATE_TEXT_SELECTOR)
                .getText().trim();
            String datePart = fullText.split(" · ")[0]
                                    .replace(",", "")
                                    .trim();

            DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern("d MMMM yyyy", new Locale("ru"));
            LocalDate parsed = LocalDate.parse(datePart, fmt);
            return Optional.of(parsed);
        } catch (DateTimeParseException | NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Проверяет, начинается ли курс в указанную дату или позже
     */
    public boolean startsOnOrAfter(LocalDate date) {
        return tryGetStartDate()
            .map(d -> !d.isBefore(date))
            .orElse(false);
    }

    /**
     * Открывает страницу курса кликом по корневому элементу (ссылке), дожидаясь интерактивности
     */
    public void openCourse() {
        // 1) Скроллим весь <a> в центр экрана
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", root);

        // 2) Находим именно видимый <h6> внутри корня
        WebElement title = root.findElement(TITLE_LOCATOR);

        // 3) Ждём, пока этот <h6> станет кликабельным
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(title));

        // 4) Кликаем по нему (не по самому <a>)
        title.click();
    }

    /**
     * Получает внутренний HTML корня для парсинга через Jsoup
     */
    public String getInnerHtml() {
        return root.getAttribute("innerHTML");
    }

    private Document getJsoupDocument() {
        return Jsoup.parse(getInnerHtml());
    }
}
