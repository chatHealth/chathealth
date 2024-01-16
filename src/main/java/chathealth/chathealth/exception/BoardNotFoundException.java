package chathealth.chathealth.exception;

import static chathealth.chathealth.constants.Constants.BOARD_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BoardNotFoundException extends ChatHealthException {

    public BoardNotFoundException() {
        super(BOARD_NOT_FOUND.getMessage());
    }

    public BoardNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return NOT_FOUND.value();
    }
}
