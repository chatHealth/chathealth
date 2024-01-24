package chathealth.chathealth.exception;

import org.springframework.http.HttpStatus;

import static chathealth.chathealth.constants.Constants.EXPIREDSESSION;

public class ExpiredSession extends ChatHealthException {

    public ExpiredSession() {
        super(EXPIREDSESSION.getMessage());
    }

    public ExpiredSession(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
