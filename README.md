## 1장 오브젝트와 의존관계

#### 자바빈

- 디폴트 생성자: 자바빈은 파라미터가 없는 디폴트 생성자가 필요, 툴이나 프레임워크에서 리플렉션을 이용해 오브잭트를 생성하기 때문.
- 프로퍼티: 자바빈이 노출하는 이름을 가진 속성. set 또는 get을 이용해 수정 또는 조회

#### JDBC를 이용하는 작업의 일반적인 순서

1. DB연결을 위한 Connection을 가져옴.
2. SQL을 담은 Statement(PreparedStatement)를 만듬.
3. 만들어진 Statement 실행.
4. SQL 쿼리 실행 결과를 ResultSet으로 받아서 정보저장할 오브젝트로 옮겨줌
5. 생성된 Connection, Statement, ResultSet과 같은 리소스는 작업 후 close.
6. 예외를 잡아서 직접 처리하거나, 메서드에 throws를 선언해서 예외 발생시 메서드 밖으로 던지게함.

- 분리와 확장을 고려한 설계
- 관심사의 분리
    - 관심이 같은 것 끼리는 하나의 객체 안으로 또는 친한 객체로 모이게 하고, 관심이 다른 것은 가능한 한 따로 떨어져서 서로 영향을 주지 않도록 분리 하는 것


- 템플릿 메서드 패턴
    - 슈퍼클래스에 기본적인 로직 흐름을 만들고 그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 메서드로 만든 뒤 서브클래스에서 필요에 맞게 구현해서 사용하는 방법
    - 변하지 않는 기능을 슈퍼클래스에 만들어두고, 확장할 기능을 서브클래스에서 만드는 것

> Example

```java
public abstract class Car {
    public int speed() {
        cylinder() * horsepower();
    }

    public abstract int cylinder();

    public abstract int horsepower();
}

public class Mustang extends Car {
    @Override
    public int cylinder() {
        return 8;
    }

    @Override
    public int horsepower() {
        return 300;
    }
}
```

- 팩토리 메서드 패턴(specialization of template method)
    - 서브클래스에서 구체적인 오브잭트 생성 방법 결정
    - 템플릿 메서드 패턴을 좀 더 활용 한 것이라 생각하면 편하다. 
    - A Factory Pattern or Factory Method Pattern says that just define an interface or abstract class for creating 
      an object but let the subclasses decide which class to instantiate. In other words, subclasses are responsible to create the instance of the class.
    - 팩토리 패턴 또는 팩토리 메서드 패턴은 그냥 오브젝트를 생성 할때 서브클래스에서 어떤 클래스의 오브잭트를 생성할지 결정하여 인터페이스 또는 추상 클래스를 정의하는 것이다.  

> Example

```java
public interface CarAbstractFactory{
  public Car createCar();
}

public class MustangFactory implements CarAbstractFactory{
  @Override
  public Car createCar(){
    return new Mustang();
  }
}

public class WranglerFactory implements CarAbstractFactory{
  @Override
  public Car createCar(){
    return new Car();
  }
}

public class MFactory implements CarAbstractFactory{
  @Override
  public Car createCar(){
    return new M();
  }
}

public class CarFactory{
  public static Car getCar(CarAbstractFactory carAbstractFactory){
    return carAbstractFactory.createCar();
  }
}

public class App {
    public static void main(String[] args) {
        Car mustang = CarFactory.getCar(new MustangFactory());
        Car wrangler = CarFactory.getCar(new WranglerFactory());
        Car m = CarFactory.getCar(new MFactory());
  
        System.out.println(car1.speed());
        System.out.println(car2.speed());
        System.out.println(car3.speed());
    }
}
```


예제)

1. getConnection()을 통해 중복코드 제거

> UserDao.java

```java
public void addUser(User user)throws ClassNotFoundException,SQLException{
        Connection connection=getConnection();
        //...
        }

public User getUser(String id)throws SQLException,ClassNotFoundException{
        Connection connection=getConnection();
        //...
        }

private Connection getConnection()throws ClassNotFoundException,SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:13306/tobi","root","root");
        }
```

2. 추상 메서드를 이용
    - Connection 연결 로직만 하위 클래스에서 구현하도록함

> UserDao.java

```java
public void addUser(User user)throws ClassNotFoundException,SQLException{
        Connection connection=getConnection();
        //...
        }

public User getUser(String id)throws SQLException,ClassNotFoundException{
        Connection connection=getConnection();
        //...
        }

public abstract Connection getConnection()throws ClassNotFoundException,SQLException;

```

3. 인터페이스를 사용

> UserDao.java

```java
public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void addUser(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        //...
    }
}

public class Application {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao(new SimpleConnectionMaker());
        //...
    }
}
```

- 인터페이스로 추상화 해놓은 통로를 통해 접근
- 실제 구현 클래스를 바꿔도 변경에 자유로움
- 다른 커넥션을 만들어도 UserDao 내부 코드의 변화는 없음.
- 추상 클래스를 사용시, 내부 코드에서 connection을 재정의 해줘야 하지만, 인터페이스를 사용시, 외부에서 갈아끼워주기만 하면 된다.
- 응집도가 높다: 변화가 일어날 때 해당 모듈에서 변하는 부분이 큰 것! (다른 모듈에서 변하는 부분이 작은 것)
    - 다른 커넥션을 생성해도 인터페이스를 사용하므로서 UserDao 모듈에서는 변화가 없게 되고 인터 페이스 구현체만 수정하면 되게끔 바뀜.
- 결합도가 낮다: 다른 오브젝트 또는 모듈과 느슨하게 연결된 형태, 최소한의 방법만 간접적 형태로 제공. 서로 독립적이고 알 필요도 없게 만들어줌.
    - UserDao 모듈은 커넥션과 인터페이스를 통해 추상적으로 연결 되어있기 때문에 서로 독립적이고 알 필요도 없다.

#### IoC(제어의 역전)

- 팩토리
    - 객체 생성 방법 결정, 그렇게 만들어진 오브젝트 반환
    - 오브잭트 생성하는 쪽과 생성된 오브젝트 사용하는 쪽의 역할과 책임 분리 목적

```java
public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);
        return userDao;
    }
}
```

- 위와 같이 코드를 짜게 되었을 때, 다른 Dao 생성 기능을 넣게 되면 `ConnectionMaker connectionMaker = new SimpleConnectionMaker();` 부분의 중복이 발생,
  메서드를 분리한다.

```java
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(simpleConnectionMaker());
    }

    public AccountDao accountDao() {
        return new AccountDao(simpleConnectionMaker());
    }
}

    public ConnectionMaker simpleConnectionMaker() {
        return new SimpleConnectionMaker();
    }
```

- 원래 ConnectionMaker의 구현 클래스를 결정하고 오브젝트를 만드는 제어권은 UserDao에 있었는데 이것을 DaoFactory에 넘김. 이것이 제어의 역전.

#### 스프링의 IoC(제어의 역전)

스프링의 핵심: Bean Factory(Application Context)

- Bean(빈): 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트, 자바빈 또는 엔터프라이즈 자바빈(EJB)의 빈과 비슷한 오브젝트 단위의 애플리케이션 컴포넌트, 스프링 빈은 스프링 컨테이너가 생성과 관계설정, 사용 등을 제어해주는 제어의 역전이 적용된 오브젝트
- Bean Factory(빈 팩토리): 빈의 생성과 관계설정 같은 제어를 담당하는 IoC 오브젝트(A.K.A Application Context)
    - 빈을 생성하고 관계를 설정하는 IoC의 기본 기능에 초점
- Application Context: IoC 방식을 따라 만들어진 일종의 빈 팩토리(A.K.A Bean Factory). 빈 팩토리를 확장 한 것.
    - 애플리케이션 전반에 걸쳐 모든 구성요소의 제어를 담당하는 IoC엔진
    - 별도로 설정정보를 담고 있는 무엇인가를 가져와 이를 활용하는 범용적인 IoC 엔진

#### 애플리케이션 컨텍스트를 만들어보자

> 스프링 빈 팩토리가 사용할 설정정보를 담는다, @Configuration

```java

@Configuration //설정정보 등록!!
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    UserDao specialUserDao() {
        //some implementation
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
```

> 애플리케이션 컨텍스트 적용

> @Configuration 이 붙은 자바 코드를 설정정보로 사용하려면 AnnotationConfigApplicationContext 사용

```java
public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);
        UserDao specialDao = context.getBean("specialUserDao", UserDao.class);
    }
}
```





#### DaoFactory를 오브잭트 팩토리로 직접 사용했을때보다 Application Context를 사용했을때의 장점

- 클라이언트는 구체적인 팩토리 클래스를 알 필요가 없다.
- 애플리케이션 컨텍스트는 종합 IoC 서비스를 제공해준다.
- 애플리케이션 컨텍스트는 빈을 검색하는 다양한 방법을 제공한다.



#### 용어 정리

- 빈: 스프링이 IoC 방식으로 관리하는 오브젝트, 스프링이 직접 생성과 제어를 담당하는 오브젝트
- 빈팩토리: IoC 담당하는 핵심 컨테이너. 등록 생성 조회 반환 등 관리하는 기능 담당. 보통은 기본적인 인터페이스의 이름이 되고, 이를 확장한 애플리케이션 컨텍스트를 이용. (보통 생성 제어 관점에서 이야기할때 빈팩토리)
- 애플리케이션 컨텍스트: 빈 팩토리를 확장한 IoC 컨테이너. 빈 팩토리에 추가로 각종 부가서비스 제공. (보통 스프링이 제공하는 애플리케이션 지능 기능 포함해서 이야기 할때 애플리케이션 컨텍스트라고 불림.) 빈 팩토리를 상속함!
- 설정정보: 애플리케이션 컨텍스트 또는 빈 팩토리가 IoC를 적용하기 위해 사용하는 메타정보.
- 컨테이너 또는 IoC컨테이너: 애플리케이션이나 빈 팩토리를 이렇게 부른다. 
- 스프링 프레임워크: IoC컨테이너, 애플리케이션 컨텍스트 포함, 스프링이 제공하는 모든 기능

## 

#### 싱글톤 레지스트리와 오브젝트 스코프

- 애플리케이션 컨택스트와 직접 만들었던 오브젝트 팩토리의 차이: (싱글톤 레지스트리)스프링은 여러번에 걸쳐 빈을 요청하더라도 동일한 오브젝트 반환

  

#### 왜 싱글톤으로 빈을 만드나?

- 서버 환경이기 때문: 매번 클라이언트에서 요청이 올때마다 오브젝트를 새로 만들어서 사용한다면, 부하가 많이 걸린다.



#### 일반적인 싱글톤 패턴의 한계

- private 생성자를 갖고 있기 때문에 상속할 수 없다.
- 테스트하기 힘들다.
- 서버에서는 싱글톤이 하나만 만들어짐을 보장하지 못한다.
- 전역 상태를 만들수 있기 때문에 바람직하지 못하다.



#### 싱글톤 레지스트리

- 싱글톤 패턴을 보완함.
- 평범한 자바 클래스를 싱글톤으로 활용하게끔 해줌.
- 테스트를 하기도 편함.
- 고전적인 싱글톤 패턴이 아니라, 싱글톤을 만들고 관리해주는 싱글톤 레지스트리!



#### 싱글톤과 오브젝트의 상태

- 멀티스레드 환경이라면 여러 스레드가 동시에 접근해서 사용할 수 있다.
- 따라서, 상태를 갖고있지 않는 무상태 방식으로 만들어야 한다.
- 읽기전용의 정보는 인스턴스 변수를 사용해도 상관 없다.
- db나 서버의 리소스로부터 생성한 정보는, 파라미터나 로컬변수, 리턴값 등을 이용해야 스레드세이프해진다.



#### 스프링 빈의 스코프

- 10장에서 자세히 알아보자



#### 의존관계 주입(DI)

- 스프링 IoC 기능의 대표적인 동작원리는 주로 의존관계 주입이라고 불린다. (초기에는 IoC컨테이너라고 불렸지만 요즘은 DI 컨테이너라고 더 많이 불림)
- 



#### 의존관계란

- A의 프로퍼티에 B가 있을때, B가 변하면 A에 영향을 미친다. 이럴땐 A가 B에 의존한다고 한다.(화살표 방향 중요)



#### UserDao에서 의존관계를 살펴보자.

- UserDao의 프로퍼티에 SimpleConnectionMaker 가 직접적으로 들어있었다.
- Interface를 사용해서, UserDao는 인터페이스에 대해서 의존관계가 바뀌었으며,
- 실질적인 인터페이스의 구현 클래스(SimpleConnectionMaker)과의 결합도는 낮아졌다.



#### 의존관계 주입이란

- 클래스 모델이나 코드에는 런타임 시점의 의존관계가 드러나지 않는다. 그러기 위해서는 인터페이스에만 의존하고 있어야 한다.
- 런타임 시점의 의존관계는 컨테이너나 팩토리 같은 제3의 존재가 결정한다.
- 의존관계는 사용할 오브젝트에 대한 레퍼런스를 외부에서 제공(주입)해줌으로써 만들어진다.



- 핵심: 오브젝트 사이의 런타임 관계를 맺어주는 책임을 지닌 제3의 존재



#### UserDao의 의존관계 주입





#### 의존관계 검색 vs 주입

- 검색 방식에서는 자신이 스프링의 빈일 필요가 없음.

```java
public UserDao(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker=context.getBean("connectionMaker",ConnectionMaker.class);
}
```

- 이 때에는 ConnectionMaker 만 스프링 빈이기만 하면 됨.
- 의존관계 주입에서는 UserDao와 ConnectionMaker 사이에 DI가 적용되려면 UserDao도 반드시 컨테이너가 만드는 빈 오브젝트여야 한다.

#### DI 기술의 장점은 무엇일까?

- 코드에는 런타임 클래스에 대한 의존관계가 나타나지 않고
- 인터페이스를 통해 결합도가 낮은 코드를 만드므로
- 다른 책임을 가진 사용 의존관계에 있는 대상이 바뀌거나 변경되어도 영향을 받지 않으며
- 변경을 통한 다양한 확장 방법에는 자유롭다.

