# 3장. 템플릿

---

## 템플릿이란?

바뀌는 성질이 다른 코드 중에서 변경이 거의 일어나지 않으며 일정한 패턴으로 유지되는 특성을 가진 부분을 자유롭게 변경되는 성질을 가진 부분으로부터 독립시켜서 효과적으로 활용할 수 있도록 하는것

---

## 템플릿 메서드 패턴의 적용

템플릿 메서드 패턴은 상속을 통해 기능을 확장해서 사용하는 부분입니다.

장점 - 클래스의 기능을 확장하고 싶을 때마다 상속을 통해 자유롭게 확장할 수 있습니다.

단점 - 확장 구조가 클래스를 설계하는 시점에서 고정되어 버립니다. 즉 서브 클래스들이 이미 클래스 레벨에서 컴파일 시점에 그 관계까 결정되어 있다는 것입니다.

> 단점에 의해 관계에 대한 유연성이 떨어져 버립니다.   
> 이는 상속을 통해 확장을 꾀하는 템플릿 메서드 패턴의 단점입니다.

---

## 전략 패턴

전략패턴이란 ?   
오브젝트를 아예 둘로 분리하고 클래스 레벨에서는 인터페이스를 통해서만 의존하도록 만드는 것입니다.

OCP 관점에서 보면 확장에 해당하는 변하는 부분을 별도의 클래스로 만들어 추상화된 인터페이스를 통해 위임하는 방식입니다.
보통 변하는 부분인 전략을 바깥에서 주입받아서 관계를 유연하게 하는 것입니다. 

---

## 의존 관계(DI)의 다양한 방법

DI의 가장 중요한 개념은 제 3자의 도움을 통해 두 오브젝트 사이의 유연한 관계가 설정되도록 만든다는 것.   
이 개념만 따른다면 DI를 이루는 오브젝트와 구성요소의 구조나 관계는 다양하게 만들 수 있습니다.


### 일반적인 DI
* 두개의 오브젝트
* 두 오브젝트의 관계를 다이나믹(동적)하게 설정해주는 오브젝트 팩토리(DI 컨테이너)
* 오브젝트 팩토리를 사용하는 클라이언트

일반적으로 DI는 위와같은 4개의 오브젝트 사이에서 일어납니다.   

### 마이크로 DI

하지만때로는 아래와 같은 형식으로 DI가 일어납니다.
* 원시적인 전략 패턴 구조를 따라 클라이언트가 오브젝트 팩토리의 책임을 함꼐 지니고 있을 수도 있습니다.
* 클라이언트와 전략(의존 오브젝트)이 결합될 수 있습니다.
* 클라이언트와 DI 관계에 있는 두 개의 오브젝트가 모두 하나의 클래스 안에 담길 수 도 있습니다.

이런 경우 DI가 매우 작은 단위의 코드와 메서드 사이에서 일어나기도 합니다.      
얼핏 보면 DI 같아 보이지 않지만 작은 단위로 DI가 이뤄지고 있음을 홗인할 수 있습니다.   

> 이렇게 DI의 장점을 단순화해서 IoC 컨테이너의 도움 없이 코드 내에서 적용한 경우를 마이크로 DI 라고 부릅니다.   
> 또는 코드에 의한 DI 라는 의미로 수동 DI 라고 부를 수도 있습니다.

---
## 전략과 클라이언트의 동거

전략 패턴을 사용해서 DI 관계를 형성했지만 DAO 메서드마다 새로운 StatementStrategy를 만들어야 한다는 단점이 있습니다.   
이래서는 템플릿 메서드 패턴을 적용했을 떄 로직마다 상속받는 새로운 클래스를 만들어줘야 하는것과 큰 차이가 없습니다.   

이를 해소하기 위해 내부클래스를 활용할 수 있습니다.   

### 중첩 클래스
* 중첩 클래스(nested class) : 다른 클래스 내부에 정의되는 클래스
    * 스테틱 클래스(static class) : 독립적 오브젝트로 만들어질 수 있는 중첩 클래스
    * 내부 클래스(inner class) : 자신이 정의된 클래스의 오브젝트 안에서만 만들어질 수 있는 중첩 클래스

### 내부 클래스
내부 클래스는 다시 범위(scope)에 따라 세 가지로 구분됩니다. 이는 선언된 위치에 따라 달라집니다. 
* 멤버 내부 클래스(member inner class) : 멤버 필드처럼 오브젝트 레벨에 정의 되는 내부 클래스
* 로컬 클래스(local class) :  메서드 레벨에 정의되는 내부 클래스
* 익명 내부 클래스(anonymous inner class) : 이름을 갖지않는 내부 클래스

```java
public void add(final User user) throws SQLException {
        StatementStrategy statementStrategy = new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into users(id, name, password) values (?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        };
        jdbcContextWithStatementStrategy(statementStrategy);
    }
```

### 익명 내부 클래스
이름을 갖지 않는 내부 클래스. 클래스 선언과 오브젝트 생성이 결합된 형태로 만들어짐   
생성자를 재사용할 필요가 없고, 구현한 인터페이스 타입으로만 사용할 경우에 편리합니다.

```java
public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(
        connection -> {
        PreparedStatement ps = connection.prepareStatement(
        "insert into users(id, name, password) values (?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        return ps;
        });
        }
```

---

## 컨텍스트와 DI

위에서 사용한 구조를 전략 패턴의 구조로 보자면
* 클라이언트(Client) : UserDao 의 메서드
* 전략(Strategy) : 익명 내부 클래스로 만들어지는 것
* 컨텍스트(Context) : jdbcContextWithStatementStrategy() 메서드

jdbcContextWithStatementStrategy() 메서드는 PreparedStatement 를 실행하는 역할을 담당하고 있으니 이는 다른 DAO 에서도 사용가능하므로 클래스 밖으로 독립시켜서 다른 클래스에서도 사용가능 하도록 만드는 것이 좋습니다.

즉, 클래스 단위의 범용성을 가진다면 클래스 분리를 통해 독립시켜주는 것입니다.

