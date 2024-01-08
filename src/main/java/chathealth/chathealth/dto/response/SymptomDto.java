package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.post.SymptomType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SymptomDto {
    private Long id;
    private SymptomType symptomName;
}
