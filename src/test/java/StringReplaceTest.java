import org.junit.jupiter.api.Test;

public class StringReplaceTest {
    @Test
    public void replaceFirstTest() {
        String id = "ID";
        String name = "NAME";
        String password = "PASSWORD";
        String sql = "insert into users (id, name, password) values (?, ?, ?)";
        sql = sql.replaceFirst("\\?", id);
        sql = sql.replaceFirst("\\?", name);
        sql = sql.replaceFirst("\\?", password);
        System.out.println("sql = " + sql);
    }

    @Test
    public void splitString() {
        String sql = "insert into users (id, name, password) values (?, ?, ?)";
        final String[] values = sql.split("values");
        for (String value : values) {
            System.out.println("value = " + value);
        }
    }
}
