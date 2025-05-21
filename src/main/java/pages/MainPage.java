package pages;

import com.google.inject.Inject;
import components.HeaderMenuComponent;
import org.openqa.selenium.WebDriver;
import io.cucumber.guice.ScenarioScoped;

/**
 * Страница «главная».
 */
@ScenarioScoped
public class MainPage extends BasePage {

    private final HeaderMenuComponent header;

    @Inject
    public MainPage(WebDriver driver, HeaderMenuComponent header) {
        super(driver);
        this.header = header;
    }

    @Override
    protected String getPath() {
        return "/";
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
