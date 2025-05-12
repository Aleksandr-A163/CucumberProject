package runners;

import di.GuiceInjectorFactory;
import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.guice.InjectorSource;

@Cucumber
@InjectorSource(GuiceInjectorFactory.class)
public class CucumberTest { }