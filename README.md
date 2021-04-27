# 우테코 3기 토비의 스프링 스터디
> 챕터 01. 오브젝트와 의존관계

<br>

## 핵심 정리
- [초난감 UserDao](https://github.com/binghe819/spring-toby-practice/blob/chapter01/src/main/java/com/binghe/user/dao/UserDao.java)

<br>

### 컴파일 타임시의 의존성이 결정되는 방식
> 구체적인 것에 의존하므로 유연하지 않다 (코드 레벨의 의존 관계)
* 의존성이 컴파일 타임때 결정된다.
    * [메서드 추출 기법](https://github.com/binghe819/spring-toby-practice/blob/chapter01/src/main/java/com/binghe/user/dao/method_extraction/MethodExtractionUserDao.java)
    * [상속을 통한 확장 - 템플릿 메서드 패턴](https://github.com/binghe819/spring-toby-practice/tree/chapter01/src/main/java/com/binghe/user/dao/template_method_pattern)
    * [클래스 분리 (분리했지만 컴파일 타임에 구체적인 것에 의존)](https://github.com/binghe819/spring-toby-practice/tree/chapter01/src/main/java/com/binghe/user/dao/class_separate)
* 단점
    * 문제점은 런타임 시의 의존관계가 코드 속에 다 미리 결정된다는 점이다. (유연하지 않음)

<br>

### 런타임시 의존성 주입해주는 방식
> 추상적인 것에 의존하므로 유연하다
* 의존성이 런타임때 결정된다.
    * [인터페이스로 분리 - OCP](https://github.com/binghe819/spring-toby-practice/blob/chapter01/src/main/java/com/binghe/user/dao/ConnectionMaker.java)
    * [인터페이스를 이용한 의존성 주입 - 전략 패턴](https://github.com/binghe819/spring-toby-practice/tree/chapter01/src/main/java/com/binghe/user/dao/dependency_injection)
    * [객체를 생성하는 관심과 사용하는 관심 분리 - 객체 팩토리 Dao Factory (설계도), IoC](https://github.com/binghe819/spring-toby-practice/tree/chapter01/src/main/java/com/binghe/user/dao/dao_factory)
    * [객체를 생성하는 관심과 사용하는 관심 분리 - 스프링 IoC (중요)](https://github.com/binghe819/spring-toby-practice/tree/chapter01/src/main/java/com/binghe/user/dao/spring_ioc)
* 장점
    * 런타임 의존관계가 컴파일 타임시에 드러나지 않는다.
    * IoC 방식을 쓰면 UserDao로부터 런타임 의존관계를 드러내는 코드를 제거하고, 제 3의 존재(IoC/DI 컨테이너)에 런타임 의존관계 결정 권한을 위임한다. (설계도)
* DI가 IoC인 이유
    * DI (의존성 주입 방식)는 자신이 사용할 객체에 대한 선택과 생성 제어권을 외부로 넘기고 자신은 수동적으로 주입받는 객체를 사용한다는 점에서 IoC의 개념에 잘 들어맞는다.

> URL을 누르면 예시로 이동합니다 :)

