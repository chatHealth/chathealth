package chathealth.chathealth.entity.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SymptomType {

    LIVER("간건강"),
    SKIN("피부건강"),
    EYE("눈건강"),
    IMMUNE("면역건강"),
    BONE("뼈건강"),
    STOMACH("위건강"),
    INTESTINE("장건강"),
    ANTIOXIDANT("항산화건강"),
    BLOOD("혈행건강"),
    VITALITY("활력건강");

    private final String symptomName;
}