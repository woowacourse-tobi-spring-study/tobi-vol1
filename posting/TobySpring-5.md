# 토비의 스프링 5장. 서비스 추상화

자바에는 사용 방법과 형식은 다르지만 기능과 목적이 유사한 기술이 존재합니다.(목적이 유사한 여러 기술이 공존하기도 합니다.)

5장에서는 DAO에 트랙잭션을 적용해보면서 스프링이 어떻게 성격이 비슷한 여러 종류의 기술을 추상화하고 이를 일관된 방법으로 사용할 수 있도록 지원하는 지를 살펴볼 것 입니다.

---

## 사용자 레벨 관리 기능 추가

사용자의 정보 관리 모듈에 사용자의 활동 내역을 참고해서 레벨을 조정해주는 기능을 추가

사용자 괸리 기능에서 구현할 비즈니스 로직 내용
* 사용자의 레벨은 BASIC, SILVER, GOLD 세 가지 중 하나다.
* 사용자가 처음 가입하면 BASIC 레벨이 되며, 이후 활동에 따라서 한 단계씩 업그레이드 될 수 있습니다.
* 가입 후 50회 이상 로그인을 하면 BASIC 에서 SILVER 레벨이 됩니다.
* SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD 레벨이 됩니다.
* 사용자 레벨의 변경 작업은 일정한 주기를 가지고 일괄적으로 진행됩니다. 변경 작업 전에는 조건을 충족하더라도 레벨의 변경이 일어나지 않습니다.

DB 에 사용자의 레벨을 "BASIC", "SILVER", "GOLD" 라고 일정한 종류의 정보를 문자열로 넣는 방식은 좋지 않습니다. (이후 등급의 이름에 변화가 있을 때 전체 DB를 수정해야합니다.)

그렇다면 각 레벨을 코드화 해서 숫자로 넣습니다.

```java
class User {
    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;
    
    int level;
    
    public void setLevel(int level) {
        this.level = level;
    }
}
```

위 코드의 문제는 1,2,3 을 제외한 다른 값을 넣어버리면 컴파일러가 잘못된 값인지 체크해주지 못한다는 단점이 있습니다.

따라서 숫자 타입보다 Enum을 활용하는 것이 좋습니다.

```java
public enum Level {
    BASIC(1), SILVER(2), GOLD(3);
    
    private final int value;
    
    Level(int value) {
        this.value = value;
    }
    
    public int intValue() {
        return this.value;
    }

    public static Level valueOf(int value) {
        return Arrays.stream(values())
                .filter(level -> level.intValue() == value)
                .findAny().orElseThrow(() -> new AssertionError("Unknown value: " + value));
    }
}
```

---

## 사용자 관리 로직을 두는 곳

DAO는 데이터를 어떻게 가져오고 조작할지를 다루는 곳   
-> DAO는 비즈니스 로직을 두는 곳이 아닙니다.

따라서 비즈니스 로직을 제공한다는 의미에서 UserService 클래스를 사용합니다.