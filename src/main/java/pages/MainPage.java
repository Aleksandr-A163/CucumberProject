package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import components.HeaderMenuComponent;

/**
 * �������� ���������.
 */
public class MainPage {

    private final WebDriver driver;
    private final HeaderMenuComponent header;

    @Inject
    public MainPage(WebDriver driver, HeaderMenuComponent header) {
        this.driver = driver;
        this.header = header;
    }

    /** ��������� ������� �������� OTUS */
    public void open() {
        driver.get("https://otus.ru");
    }

    /**
     * ���������� �������� ���� ��������� � ���������.
     */
    public void openLearningMenu() {
        header.openLearningMenu();
    }

    /**
     * ���������� ����� � ���� ��������� ���������.
     */
    public String clickRandomCategory() {
        return header.clickRandomCategory();
    }
}