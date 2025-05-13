package steps;

import io.cucumber.java.After;
import driver.WebDriverProvider;

public class Hooks {

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2_000);            // ждем 2 секунды, чтобы вы успели увидеть окно
        WebDriverProvider.quitDriver();
    }
}