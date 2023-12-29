package chathealth.chathealth.exception;

public class UserNotFound extends ChatHealthException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

}
