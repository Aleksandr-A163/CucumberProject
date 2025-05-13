package di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.cucumber.guice.CucumberModules;
import io.cucumber.guice.InjectorSource;

public class GuiceInjectorFactory implements InjectorSource {
    private static final Injector injector = Guice.createInjector(
        // этот модуль подхватит все Cucumber-Guice скоупы, включая ScenarioScope
        CucumberModules.createScenarioModule(),
        // ваш модуль с привязками WebDriverProvider, WebDriver, страниц и компонентов
        new TestModule()
    );

    @Override
    public Injector getInjector() {
        return injector;
    }
}