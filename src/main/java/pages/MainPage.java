package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import components.HeaderMenuComponent;

/**
 * Страница «главная».
 */
public class MainPage {

    private final WebDriver driver;
    private final HeaderMenuComponent header;

    @Inject
    public MainPage(WebDriver driver, HeaderMenuComponent header) {
        this.driver = driver;
        this.header = header;
    }

    /** Открывает главную страницу OTUS */
    public void open() {
        driver.get("https://otus.ru");
    }

    /**
     * Делегирует открытие меню «Обучение» в компонент.
     */
    public void openLearningMenu() {
        header.openLearningMenu();
    }

    /**
     * Делегирует выбор и клик случайной категории.
     */
    public String clickRandomCategory() {
        return header.clickRandomCategory();
    }
}