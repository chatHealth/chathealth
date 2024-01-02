package chathealth.chathealth.exception;

public class PasswordNotEqual extends ChatHealthException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotEqual() {
        super(MESSAGE);
    }

    public PasswordNotEqual(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
