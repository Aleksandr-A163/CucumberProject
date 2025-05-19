package pages;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CoursePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Актуальный селектор заголовка курса
    private final By titleSelector = By.cssSelector("h1.diGrSa");
    private static final By PRICE_SELECTOR = By.cssSelector("div.course-price > span.price-value");

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

    /** Парсит и возвращает цену в рублях */
    public int getPrice() {
        WebElement priceEl = wait.until(ExpectedConditions.visibilityOfElementLocated(PRICE_SELECTOR));
        String text = priceEl.getText();            // например "12 490 ₽"
        String digits = text.replaceAll("\\D+", ""); // "12490"
        return Integer.parseInt(digits);
    }


}