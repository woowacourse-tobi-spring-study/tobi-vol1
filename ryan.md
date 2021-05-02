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
    
- 팩토리 메서드 패턴
    - 서브클래스에서 구체적인 오브잭트 생성 방법 결정
    
둘의 차이: https://hojak99.tistory.com/347

예제)
1. getConnection()을 통해 중복코드 제거
> UserDao.java
```java
public void addUser(User user) throws ClassNotFoundException, SQLException {
    Connection connection = getConnection();
    //...
}

public User getUser(String id) throws SQLException, ClassNotFoundException {
     Connection connection = getConnection();
     //...
}

private Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection("jdbc:mysql://localhost:13306/tobi", "root", "root");
}
```

2. 추상 메서드를 이용
    - Connection 연결 로직만 하위 클래스에서 구현하도록함

> UserDao.java

```java
public void addUser(User user) throws ClassNotFoundException, SQLException {
    Connection connection = getConnection();
    //...
}

public User getUser(String id) throws SQLException, ClassNotFoundException {
     Connection connection = getConnection();
     //...
}

public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

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


#### IoC(제어의 역전)
- 