package chathealth.chathealth.exception;

import lombok.Getter;

@Getter
public abstract class ChatHealthException extends RuntimeException {

    public ChatHealthException(String message) {
        super(message);
    }

}
