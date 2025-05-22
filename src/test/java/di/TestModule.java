package di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import driver.WebDriverProvider;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        // 1) Провайдер браузера — единый Singleton
        bind(WebDriverProvider.class)
            .in(Singleton.class);

        // 2) WebDriver в рамках одного Cucumber-сценария
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class)
            .in(ScenarioScoped.class);

        // (Не нужно бинжить страницы/компоненты — они аннотированы @ScenarioScoped)
    }
}
