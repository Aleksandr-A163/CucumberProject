package steps;

import io.cucumber.java.en.Given;

public class BrowserSteps {
    @Given("� ������������ ������� {string}")
    public void setBrowser(String browser) {
        System.setProperty("browser", browser);
    }
}