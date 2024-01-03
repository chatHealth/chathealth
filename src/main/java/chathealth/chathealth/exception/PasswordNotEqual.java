package chathealth.chathealth.exception;

import static chathealth.chathealth.constants.Constants.PASSWORD_NOT_EQUAL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class PasswordNotEqual extends ChatHealthException {

    public PasswordNotEqual() {
        super(PASSWORD_NOT_EQUAL.getMessage());
    }

    public PasswordNotEqual(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return BAD_REQUEST.value();
    }
}
