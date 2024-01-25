package chathealth.chathealth.exception;

import org.springframework.http.HttpStatus;

import static chathealth.chathealth.constants.Constants.EXPIRED_SESSION;

public class ExpiredSession extends ChatHealthException {

    public ExpiredSession() {
        super(EXPIRED_SESSION.getMessage());
    }

    public ExpiredSession(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
