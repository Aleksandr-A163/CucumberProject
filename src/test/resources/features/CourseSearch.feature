Feature: Поиск курса
  Описание: Проверяем работу поиска курса по названию

  Scenario: Найти существующий курс
    Given I am on the main page
    When I search for course "Active Testing"
    Then I see course "Active Testing" in the results