package pages;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.regex.Pattern;

import java.time.Duration;

public class CoursePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Актуальный селектор заголовка курса
    private final By titleSelector =
            By.cssSelector("h1.diGrSa");

    // точный локатор блока с цифрами и ₽
    private static final By PRICE_VALUE =
        By.cssSelector("div.sc-153sikp-9.cKOPJA > div.sc-153sikp-11.gztHyx");

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1) Ждём, что нода станет видимой
        WebElement priceEl = wait
            .until(ExpectedConditions.visibilityOfElementLocated(PRICE_VALUE));

        // 2) Ждём, что у этого элемента появится хотя бы одна цифра
        wait.until(ExpectedConditions.textMatches(
            PRICE_VALUE,
            Pattern.compile(".*\\d+.*")
        ));

        // 3) Скроллим к нему, чтобы макет не мешал
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", priceEl);

        // 4) Парсим текст
        String raw = priceEl.getText().trim();
        if (raw.equalsIgnoreCase("Бесплатно")) {
            return 0;
        }
        String digits = raw.replaceAll("\\D+", "");
        return Integer.parseInt(digits);
    }


}