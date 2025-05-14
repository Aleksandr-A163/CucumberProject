package context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TestContext {

    private final ScenarioContext scenarioContext;

    @Inject
    public TestContext() {
        this.scenarioContext = new ScenarioContext();
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}