package steps;

import io.cucumber.java.en.Given;

public class BrowserSteps {
    @Given("Я устанавливаю браузер {string}")
    public void setBrowser(String browser) {
        System.setProperty("browser", browser);
    }
}