package di;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class InjectorFactory {

    private static final Injector injector = Guice.createInjector(new TestModule());

    private InjectorFactory() {}

    public static Injector getInjector() {
        return injector;
    }
}