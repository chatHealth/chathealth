package chathealth.chathealth.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EntIndexDto {

    private Long id;

    private String ceo;

    private String company;

    private String profile;

    @Builder
    public EntIndexDto(Long id, String ceo, String company, String profile) {
        this.id = id;
        this.ceo = ceo;
        this.company = company;
        this.profile = profile;
    }
}
