Feature: 카테고리

  Scenario: 카테고리를 생성한다
    When "식품" 카테고리를 생성한다
    Then 응답 상태코드는 200이다
    And 응답의 "name"은 "식품"이다

  Scenario: 카테고리를 전체 조회한다
    Given "식품" 카테고리가 존재한다
    And "패션" 카테고리가 존재한다
    When 카테고리를 전체 조회한다
    Then 응답 상태코드는 200이다
    And 응답 목록에 "name"이 "식품", "패션"을 포함한다

  Scenario: 카테고리가 없으면 빈 리스트를 반환한다
    When 카테고리를 전체 조회한다
    Then 응답 상태코드는 200이다
    And 응답 목록이 비어있다
