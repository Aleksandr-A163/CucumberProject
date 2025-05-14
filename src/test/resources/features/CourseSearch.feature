Feature: Поиск курса
  Как пользователь каталога курсов
  Я хочу найти и открыть случайный курс из списка

  Scenario: Открыть случайный курс из заранее заданных
    Given Я открываю каталог курсов
    When Я выбираю случайный курс из списка:
      | Kotlin QA Engineer   |
      | Administrator Linux. Basic      |
      | PHP Developer. Professional |
    Then Открывается страница выбранного курса