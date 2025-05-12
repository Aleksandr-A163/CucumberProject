package di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import driver.WebDriverProvider;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.CourseCatalogPage;
import pages.CoursePage;
import components.HeaderMenuComponent;
import components.CourseListComponent;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        // 1) Провайдер браузера — единый singleton
        bind(WebDriverProvider.class)
            .in(Singleton.class);

        // 2) WebDriver в рамках одного Cucumber-сценария
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class)
            .in(ScenarioScoped.class);

        // 3) Все страницы и компоненты тоже «живут» внутри сценария
        bind(MainPage.class).in(ScenarioScoped.class);
        bind(CourseCatalogPage.class).in(ScenarioScoped.class);
        bind(CoursePage.class).in(ScenarioScoped.class);
        bind(HeaderMenuComponent.class).in(ScenarioScoped.class);
        bind(CourseListComponent.class).in(ScenarioScoped.class);
    }
}