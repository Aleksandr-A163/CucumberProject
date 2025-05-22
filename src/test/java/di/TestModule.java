package di;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.cucumber.guice.CucumberModules;
import io.cucumber.guice.InjectorSource;
import org.openqa.selenium.WebDriver;
import com.google.inject.Singleton;
import io.cucumber.guice.ScenarioScoped;
import driver.WebDriverProvider;

public class TestModule extends AbstractModule implements InjectorSource {

    @Override
    protected void configure() {
        bind(WebDriverProvider.class).in(Singleton.class);
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class)
            .in(ScenarioScoped.class);
    }

    /**
     * Этот метод будет вызван Cucumber-Guice
     * вместо создания своего InjectorSource-класса.
     */
    @Override
    public Injector getInjector() {
        return com.google.inject.Guice.createInjector(
            Stage.PRODUCTION,
            CucumberModules.createScenarioModule(),  // поддержка @ScenarioScoped
            this                                    // ваш модуль с биндингами
        );
    }
}
