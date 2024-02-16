package chathealth.chathealth.exception;

import chathealth.chathealth.constants.Constants;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class RoomNotFound extends ChatHealthException {

    public RoomNotFound() {
        super(Constants.ROOM_NOT_FOUND.getMessage());
    }

    @Override
    public int getStatusCode() {
        return NOT_FOUND.value();
    }
}
