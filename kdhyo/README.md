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