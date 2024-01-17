package chathealth.chathealth.exception;

import org.springframework.http.HttpStatus;

import static chathealth.chathealth.constants.Constants.NOT_EXIST_FILE;

public class NotExistFile extends ChatHealthException {

    public NotExistFile() {
        super(NOT_EXIST_FILE.getMessage());
    }

    public NotExistFile(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}