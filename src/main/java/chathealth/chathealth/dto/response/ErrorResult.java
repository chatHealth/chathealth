package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResult {

    private String message;
    private String code;
    private Map<String, String> validation;

    public void addValidError(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
