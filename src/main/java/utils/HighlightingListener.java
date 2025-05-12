package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlightingListener implements WebDriverListener {

    private void highlight(WebElement element) {
        WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].style.border='3px solid red'", element);
    }

    @Override
    public void beforeClick(WebElement element) {
        highlight(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlight(element);
    }
}