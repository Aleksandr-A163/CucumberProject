Feature: Запуск браузера
  Проверяем, что выбранный браузер открывается корректно

  Scenario Outline: Запуск браузера <browser> и проверка запуска
    When Я открываю браузер "<browser>"
    Then браузер запущен корректно

  Examples:
    | browser |
    | Chrome  |
    | Firefox |
