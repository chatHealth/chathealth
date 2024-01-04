package chathealth.chathealth.exception;

import org.springframework.http.HttpStatus;

import static chathealth.chathealth.constants.Constants.FORBIDDEN;

public class NotPermitted extends ChatHealthException {

    public NotPermitted() {
        super(FORBIDDEN.getMessage());
    }

    public NotPermitted(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
