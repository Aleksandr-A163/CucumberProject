package di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.cucumber.guice.InjectorSource;  // <-- теперь главная «целевая» библиотека есть

public class GuiceInjectorFactory implements InjectorSource {
    private static final Injector injector =
        Guice.createInjector(new TestModule());

    // public конструктор (компилятор сам создаст, если его совсем убрать)
    public GuiceInjectorFactory() {}

    @Override
    public Injector getInjector() {
        return injector;
    }
}