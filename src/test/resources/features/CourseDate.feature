Feature: Поиск курсов по дате старта
  Как пользователь каталога
  Я хочу увидеть все курсы, начинающиеся в указанную дату или позже

  Scenario: Курсы, стартующие с конкретной даты или позже
    Given Я открываю каталог курсов
    When Я фильтрую курсы по дате старта "2025-05-14"
    Then В консоль выведены названия и даты этих курсов