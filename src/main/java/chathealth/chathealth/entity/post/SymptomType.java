package chathealth.chathealth.entity.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SymptomType {

    INTESTINE("장건강"),
    LIVER("간건강"),
    SKIN("피부건강"),
    IMMUNE("면역건강"),
    BONE("뼈건강"),
    EYE("눈건강"),
    ANTIOXIDANT("항산화건강"),
    BLOOD("혈행건강"),
    VITALITY("활력건강"),
    DIET("다이어트"),;

    private final String symptomName;
}