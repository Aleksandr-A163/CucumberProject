package pages;

import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CoursePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Актуальный селектор заголовка курса
    private final By titleSelector = By.cssSelector("h1.diGrSa");
    // Новый селектор для <p> с днём и месяцем
    private static final String DATE_P_SELECTOR = "p.sc-1x9oq14-0.sc-3cb1l3-0.doSDez.dgWykw";

    @Inject
    public CoursePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getCourseTitle() {
        WebElement titleElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(titleSelector)
        );
        return titleElement.getText().trim();
    }

    public boolean isCorrectCourseOpened(String expectedTitle) {
        return getCourseTitle().equalsIgnoreCase(expectedTitle);
    }

    /**
     * Парсит дату старта курса из тега
     * <p class="sc-1x9oq14-0 sc-3cb1l3-0 doSDez dgWykw">24 апреля</p>
     * и дополняет годом, переданным в expectedYear.
     *
     * @param expectedYear год, который подставляем к дню и месяцу
     * @return LocalDate собранный из текста <p> и expectedYear
     */
    public LocalDate getCourseStartDateJsoup(int expectedYear) {
        // Ждём, пока <p> появится на странице
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(DATE_P_SELECTOR)));

        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        // Ищем именно <p> с днём и месяцем
        Element dateElement = doc.selectFirst(DATE_P_SELECTOR);
        if (dateElement == null) {
            throw new IllegalStateException(
                "Тег даты старта курса не найден: " + DATE_P_SELECTOR
            );
        }

        String dayMonth = dateElement.text().trim();          // например "24 апреля"
        String rawDate = String.format("%s, %d", dayMonth, expectedYear);
        System.out.println(">>> Дата со страницы курса (Jsoup): " + rawDate);

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMMM, yyyy", new Locale("ru"));
        return LocalDate.parse(rawDate, formatter);
    }
}