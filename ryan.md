## 1장 오브젝트와 의존관계
### 1.1 초난감 DAO
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

