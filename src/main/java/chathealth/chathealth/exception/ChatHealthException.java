package chathealth.chathealth.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ChatHealthException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public ChatHealthException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String errorMessage) {
        validation.put(fieldName, errorMessage);
    }
}
