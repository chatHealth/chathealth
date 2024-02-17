package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.post.SymptomType;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MaterialSymptomDto {

    private final SymptomType symptomName;

    private final List<String> materialName;

    public MaterialSymptomDto(SymptomType symptomName, List<String> materialName) {
        this.symptomName = symptomName;
        this.materialName = materialName;
    }
}
