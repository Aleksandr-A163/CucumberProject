Feature: ����� �����
  ��������: ��������� ������ ������ ����� �� ��������

  Scenario: ����� ������������ ����
    Given I am on the main page
    When I search for course "Active Testing"
    Then I see course "Active Testing" in the results