package user.service;

public class TestUserServiceException extends RuntimeException {
    public TestUserServiceException() {
        super("Test User Service 실패!");
    }
}
