package chathealth.chathealth.exception;

public class PasswordNotEqualException extends ChatHealthException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotEqualException() {
        super(MESSAGE);
    }

    public PasswordNotEqualException(String message) {
        super(message);
    }
}
