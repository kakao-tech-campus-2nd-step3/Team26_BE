# Team26_BE
26조 백엔드


### 코드리뷰 요청 링크
https://www.notion.so/830084caab6c4d7bab816740974e263f#1ea368223b1849baa55991c4fbc55c0b

![image](https://github.com/user-attachments/assets/2f32ac86-0817-47fb-af3b-7f488c4e58ad)

### 기본적인 ERD 설계가 잘 되어있는지 궁금합니다.

- Image : curation 과 event에서는 여러 개의 이미지를 등록할 수 있습니다. 또한, curation에서는 아래와 같은 대표 이미지가 따로 필요합니다. 이 경우 이미지 테이블을 어떻게 생성해야 하는 지 궁금합니다.
    
    ![image](https://github.com/user-attachments/assets/9815bb8c-6e9a-4341-ac3b-157fc9a8accb)

    
- 일반 멤버와 큐레이터는 ‘큐레이터는 글을 쓸 수 있다’ 이 한 가지 기능 차이만 있습니다. 이 경우 큐레이터 테이블과 일반 멤버 테이블을 합쳐야 하는 걸까요?
- 상호베타적 관계를Q래스가 있고, 그 필드가 모두 NotNull일때(하나의 생성자만 사용하는 경우) 도 @Builder 어노테이션을 적용하는것이 적절한지 궁금합니다.
    - Image 테이블을 보시면 attach_id 속성이 있는데 여기에는 curation/event/member id가 모두 들어갈 수 있습니다. 모든 테이블의 primary key로 UUID를 사용하기로 해서 혼용해서 저장할 수 있을 것 같아서입니다.
    - 이런 경우 Jpa 코드로 어떻게 구현되어야 하는지 궁금합니다.
- ERD에 나와있는 host, curator, member를 jpa로 어떻게 매핑해야 하는지 궁금합니다.


### 빌더 패턴 관련하여 궁금합니다.

1. 4~5개 이상의 필드를 가지는 클래스가 있고, 그 필드가 모두 NotNull이어야 합니다. 즉 모든 필드를 초기화하는 생성자(AllArgsConstructor) 하나만  있으면 되는 상황인데, 이때  @Builder 어노테이션을 적용해 빌더를 생성하는 것이 적절한지 궁금합니다.  `MyClass.builder().field1(val1).field2(val2)...field_n(val_n).build()` 와 같이 모든 필드에 대한 메서드를 호출해야만 하는데, 이런 경우 그냥 생성자 호출로 `MyClass(val1, val2, ... , val_n)` (파라미터수는 5개 이상일 수 있음) 와 같은 방법이 더 나을까요?
2. 위 상황에서 빌더로 필드를 초기화하는 과정에서 실수로 필수 필드를 빼먹은 경우, 즉 예를 들어 7번째 필드에 대한 `.field_7(val7)` 을 빼먹은 채 `.build()` 를 호출하였을 때 이를 감지하는 가장 나은 방법이 무엇인지 궁금합니다. `@NotNull` / `@Column(nullable = false)` / `Objects.requireNonNull(field)` 등등의 방법을 생각해 봤는데 더 나은 방법이 있을까요? 
3. 빌더로 초기화하다가 필수 필드를 빼먹는 실수와, 빌더를 사용하지 않고 생성자만 있을 때 파라미터 순서나 개수 등을 헷갈리는 실수 중 어떤 실수가 더 치명적인지 궁금합니다. 2번과 같은 상황을 고려하여, 1번과 같은 클래스에 대해서는 빌더 패턴을 적용하지 않는 것이 좋을까요?


### Enum 관련하여 궁금합니다.

```java
package org.ktc2.cokaen.wouldyouin.domain;

public enum Category {
    밴드,
    연극,
    뮤지컬,
    원데이클래스,
    전시회
}
```

- domain 패키지의 Category.java에서, 위처럼 Enum 타입으로 한글을 사용해도 괜찮은 건지 궁금합니다. 데이터베이스에서도 한글로 저장되게 할 예정입니다.
