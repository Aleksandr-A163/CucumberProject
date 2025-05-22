package di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import driver.WebDriverProvider;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        // без биндингов WebDriver — всё делаем через @Provides
        bind(WebDriverProvider.class).in(com.google.inject.Singleton.class);
    }

    @Provides
    @ScenarioScoped
    public WebDriver provideWebDriver(WebDriverProvider provider) {
        return provider.get();
    }
}
