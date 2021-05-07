# 우테코 3기 토비의 스프링 스터디

> 토비의 스프링 vol 1 직접 구현해보기!
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
* 장점
    * 런타임 의존관계가 컴파일 타임시에 드러나지 않는다.
    * IoC 방식을 쓰면 UserDao로부터 런타임 의존관계를 드러내는 코드를 제거하고, 제 3의 존재(여기선 DaoFactory)에 런타임 의존관계 결정 권한을 위임한다. (설계도)

<br>

### 라이브러리와 프레임워크
* 제어
    * 라이브러리를 사용하는 코드는 애플리케이션 흐름을 직접 제어한다. (능동)
    * 프레임워크는 거꾸로 코드가 프레임워크에 의해 사용된다. (수동) - **제어의 역전**
        * 컴포넌트의 생성과 관계 설정, 사용, 생명주기 관리등을 관장한다. (재사용성도 좋다)
        * 개발자는 프레임워크 흐름중 일부를 작성하고 프레임워크가 전체적인 흐름을 주도한다.

<br>

### IoC
* 프로그램의 제어 흐름 구조를 뒤바꾸는 것.
    * 기존엔 main부터 시작해서 모든 객체가 능동적으로 new를 통해 자신이 사용할 클래스를 결정하고 관리했다. (일반적인 제어 흐름)
    * IoC는 이러한 제어 흐름 구조를 뒤바꾼 것이다. (능동 -> 수동)
        * IoC 개념은 스프링외에도 소프트웨어 전반에 걸쳐 많이 사용된다고 한다. (프레임 워크, ...)
    * 모든 제어 권한을 자신이 아닌 다른 대상에게 위임하는 것.
* 객체지향에서의 IoC => 객체를 사용하는 책임과 생성하는 책임을 분리하고, 생성하는 책임을 다른 대상(여기선 DaoFactory)에게 위임하는 것.
    * 제어의 역전에서 객체는 자신이 사용할 오브젝트를 스스로 선택하지 않고, 주입 받는다.
        * 객체는 추상적인 것(인터페이스)에 의존하고 있고, 런타임때 다른 대상(DaoFactory)에 의해 수동적으로 결정된다.
    * 모든 객체는 제어 권한을 갖는 특별한 객체(IoC 컨테이너)에 의해 결정되고 만들어진다.
 
> 개인적으로 스프링이 IoC를 도입한 이유는, 객체 생성과 관련된 책임은 설계도와 같이 따로 분리하고, 객체 사용과 도메인 구현에 집중할 수 있도록 하기 위함이라 생각든다.

