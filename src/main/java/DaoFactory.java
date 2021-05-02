public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(new ConnectionMakerImpl());
    }
}
