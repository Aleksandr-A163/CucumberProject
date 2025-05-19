# 🧪 Автотесты Cucumber для OTUS.ru

Этот проект содержит автоматизированные тесты для сайта [otus.ru](https://otus.ru), реализованные с использованием **Java 17**, **Cucumber BDD**, **Selenium WebDriver 4+**, **JUnit 5**, **Google Guice (DI)** и **Gradle**.


##  :computer: Используемый стек

<p align="center">
<a href="https://www.jetbrains.com/idea/"><img width="10%" title="IntelliJ IDEA" src="media/logo/Intelij_IDEA.svg"></a>
<a href="https://www.java.com/"><img width="10%" title="Java" src="media/logo/Java.svg"></a>
<a href="https://www.selenium.dev/"><img width="10%" title="Selenium" src="media/logo/Selenium.svg"></a>
<a href="https://cucumber.io/"><img width="10%" title="Cucumber" src="media/logo/Cucumber.svg"></a>
<a href="https://gradle.org/"><img width="10%" title="Gradle" src="media/logo/Gradle.svg"></a>
<a href="https://junit.org/junit5/"><img width="10%" title="JUnit5" src="media/logo/JUnit5.svg"></a>
</p>

<div align="center">

**Java 17** (Toolchain)  
**Selenium WebDriver 4+**  
**WebDriverManager** (io.github.bonigarcia)  
**Cucumber BDD** (io.cucumber)  
**JUnit 5** (junit-jupiter, cucumber-junit-platform-engine)  
**Google Guice** (cucumber-guice)  
**Jsoup** (HTML-парсинг)  
**Gradle** (build automation)  
**Checkstyle** (статический анализ кода)  
**SpotBugs** (статический анализ кода)  

</div>

## 📦 Структура проекта

```
CucumberProject/
├─ src/
│  ├─ main/java/
│  │  ├─ components/        # Web-компоненты (CourseListComponent, CourseCardComponent, HeaderMenuComponent)
│  │  ├─ pages/             # Page Object (MainPage, CourseCatalogPage, CoursePage, CourseInfo)
│  │  ├─ driver/            # WebDriver Provider (WebDriverProvider, BrowserFactory, BrowserType)
│  │  └─ utils/             # Утилиты (HighlightingListener)
│  ├─ test/java/
│  │  ├─ context/           # Cucumber контекст (ScenarioContext, TestContext)
│  │  ├─ di/                # DI для тестов (TestModule, GuiceInjectorFactory)
│  │  ├─ runners/           # Тестовый раннер (CucumberTest)
│  │  └─ steps/             # Step Definitions (BrowserSteps, CourseSearchSteps, CourseDateSteps, NavigationMenuSteps, Hooks)
│  └─ test/resources/
│     ├─ features/          # Файлы сценариев (BrowserSelection, CourseSearch, CourseDate и т.д.)
│     ├─ cucumber.properties
│     └─ junit-platform.properties
├─ build.gradle
├─ settings.gradle
├─ gradlew
├─ gradlew.bat
└─ ... (конфигурация Checkstyle, SpotBugs)
```

## 🚀 Быстрый старт

1. **Клонировать репозиторий**

   ```bash
   git clone https://github.com/Aleksandr-A163/CucumberProject.git
   cd CucumberProject
   ```

2. **Запустить BDD-сценарии**

   ```bash
   ./gradlew clean cucumber
   ```

3. **Запустить все проверки (Checkstyle, SpotBugs и BDD-сценарии)**

   ```bash
   ./gradlew clean check
   ```

4. **Запуск в конкретном браузере**

   По умолчанию используется Chrome. Для запуска в Firefox или Edge:

   ```bash
   ./gradlew cucumber -Dbrowser=firefox
   ./gradlew cucumber -Dbrowser=edge
   ```

5. **Открытие отчётов**

   После выполнения сценариев локально откройте отчёт:

   ```
   build/reports/tests/test/index.html
   ```

---

## ✅ Реализованные фичи

* **Browser Selection**: выбор браузера через systemProperty `browser`
* **Cucumber BDD**: сценарии для поиска курса, фильтрации по дате и навигации по меню
* **Dependency Injection**: Google Guice (`TestModule`, `GuiceInjectorFactory`)
* **Cucumber Context**: `ScenarioContext` и `TestContext` для передачи данных между степами
* **Page Object Model + Component Based Design**
* **HTML-парсинг с Jsoup** для получения и обработки данных о курсах
* **HighlightingListener**: подсветка элементов перед действиями
* **Checkstyle + SpotBugs**: интеграция статических анализаторов кода


---

## 📧 Обратная связь

Автор: Aleksandr Anosov
GitHub: [Aleksandr-A163](https://github.com/Aleksandr-A163)
