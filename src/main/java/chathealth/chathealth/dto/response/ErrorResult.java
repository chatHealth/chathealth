package chathealth.chathealth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResult {

    private String message;
    private String code;
    @Builder.Default
    private Map<String, String> validation = new HashMap<>();

    public void addValidError(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
