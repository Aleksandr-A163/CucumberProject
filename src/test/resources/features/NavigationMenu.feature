Feature: Навигационное меню
  Описание: Проверяем, что пункты меню на главной странице соответствуют ожиданиям

  Scenario: Проверить пункты меню
    Given I am on the main page
    Then I see the navigation menu items:
      | Main page        |
      | Catalog          |
      | Cases            |
      | Forum            |
      | Blog             |
      | Personal Cabinet |