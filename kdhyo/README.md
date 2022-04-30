# 자바 ORM 표준 JPA 프로그래밍 - 기본편

## JPA
- Java Persistence API
- 자바 진영의 ORM 기술 표준

## ORM
- Object-relational mapping (객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에서는 대부분 ORM 기술이 존재
  - nodeJS - typeORM, Sequence ...

## JPA 사용 이유
- SQL 중심적인 개발에서 -> 객치 중심으로 개발
- 생산성 증가
- 유지보수성 증가
- 패러다임의 불일치 해결
- 성능 향상
- 데이터 접근 추상화와 벤더 독립성
- 표준

### 생산성
- 저장 : jpa.persist(member)
- 조회 : Member member = jpa.find(memberId)
- 수정 : member.setName("변경할 이름")
- 삭제 : jpa.remove(member)

### 유지보수성
- SQL : 필드 변경 시 모든 SQL 수정
- JPA : 필드 변경 시 필드만 수정

### 패러다임의 불일치 해결
- sql로 상속관계일 시 join, insert를 신경써야 하는 것들을 컬렉션 형식으로 풀어서 할 수 있다.

### 성능 최적화 기능
- 1차 캐시와 동일성(identity) 보장
- 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
- 지연 로딩(Lazy Loading)

#### 1차 캐시와 동일성 보장
1. 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상 (SQL 1번 실행 후 캐시에 저장)
2. DB Isolation Level이 Read Commit 이어도 애플리케이션에서 Repeatable Read 보장

#### 트랜잭션을 지원하는 쓰기 지연 - INSERT
1. 트랜잭션을 커밋할 때까지 INSERT SQL 을 모음.
2. JDBC Batch SQL 기능을 사용해서 한 번에 SQL 전송

#### 지연로딩과 즉시로딩
- 지연로딩 : 객체가 실제 사용될 때 로딩
- 즉시로딩 : JOIN SQL 로 한 번에 연관된 객체까지 미리 조회

## JPQL
- 테이블이 아닌 `객체를 대상으로 검색하는 객체 지향 쿼리`
- SQL을 추상화해서 특정 데이터베이스 SQL 에 의존 x
- JPQL 을 한마디로 정의하면 `객체 지향 SQL`

## 플러시 (flush)
- 영속성 컨텍스트를 비지 않는다.
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화한다.
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 된다.

## @Lob
- 데이터베이스 BLOB, CLOB 타입과 매핑
  - @Lob 에는 지정할 수 있는 속성이 없다.
  - 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
    - CLOB: String, char[], java.sql.CLOB
    - BLOB: byte[], java.sql.BLOB

## 기본 키 생성 전략 (@GeneratedValue)
- AUTO: 방언에 따라서 자동 지정 기본 값
- IDENTITY: 데이터베이스에 생성 위임, mysql
- SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, oracle
  - @SequenceGenerator 필요
- TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
  - @TableGenerator 필요

### IDENTITY 전략 특징
- 기본 키 생성을 데이터베이스에 위임한다.
- 주로 MySQL, PostgreSQL, SQL Server, DB2 에서 사용
  - 예) MySQL 의 AUTO_INCREMENT)
- JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
- AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음.
- IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회

### SEQUENCE 전략 특징
- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터 오브젝트
  - 예) 오라클 시퀀스
- 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용

### TABLE 전략 특징
- 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
- 장점: 모든 데이터베이스에 적용 가능
- 단점: 성능

## 연관관계 주인과 mappedBy
- mappedBy = JPA 멘탈붕괴 난이도
- mappedBy는 처음에는 이해하기 어렵다.
- 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.

### 연관관계의 주인(Owner)

양방향 매핑 규칙
- 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
- 연관관계의 주인만이 외래 키를 관리(등록, 수정)
- 주인이 아닌쪽은 읽기만 가능
- 주인은 mappedBy 속성 사용X
- 주인이 아니면 mappedBy 속성으로 주인 지정

### 누구를 주인으로?
- 외래 키가 있는 곳을 주인으로 정해라.
  - 외래키가 그렇게 아닐 경우 : Team, Member 일 경우 Team 객체에서 Member를 변경했는데, Member 테이블이 변경이 된다?!
- ManyToOne 쪽이 주인이 된다.

### 연관관계 시 어디서 설정해야할까?
- 연관관계 주인인 곳에서만 데이터가 변경이 일어난다.
- 역방향에서 수정 시에도 디비에 날라가지 않는다.
  - 예) team.addMembers(member);
  - 양방향 연관관계일 경우에는 양쪽에다 셋팅해줘야한다.
    - 아닐 시 문제점 : flush(), clear()를 하지 않을 경우에는 1차캐시에서 가져올 경우 제대로된 값을 불러오지 않을 경우가 있다.

### 양방향 연관관계 주의 점
- 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자.
- 연관관계 편의 메소드를 생성하자.
- 양방향 매핑 시에 무한 루프를 조심하자.
  - 예) toString(), lombok, JSON 생성 라이브러리