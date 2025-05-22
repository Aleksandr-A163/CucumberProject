package di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import driver.WebDriverProvider;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebDriverProvider.class).in(Singleton.class);
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class)
            .in(ScenarioScoped.class);
    }
}

