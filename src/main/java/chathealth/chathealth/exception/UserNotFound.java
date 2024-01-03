package chathealth.chathealth.exception;

import chathealth.chathealth.constants.Constants;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFound extends ChatHealthException {

    public UserNotFound() {
        super(Constants.USER_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return NOT_FOUND.value();
    }
}
