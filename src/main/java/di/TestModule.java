package di;

import com.google.inject.AbstractModule;
import driver.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.CourseCatalogPage;
import pages.CoursePage;
import components.HeaderMenuComponent;
import components.CourseListComponent;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        // Провайдер — единый, но WebDriver без скопа
        bind(WebDriver.class)
            .toProvider(WebDriverProvider.class);

        // Страницы и компоненты — тоже без скопа
        bind(MainPage.class);
        bind(CourseCatalogPage.class);
        bind(CoursePage.class);

        bind(HeaderMenuComponent.class);
        bind(CourseListComponent.class);
    }
}