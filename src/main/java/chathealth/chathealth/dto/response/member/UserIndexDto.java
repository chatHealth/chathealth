package chathealth.chathealth.dto.response.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIndexDto {

    private Long id;

    private String name;

    private String nickname;

    private String profile;

    @Builder
    public UserIndexDto(Long id, String name, String nickname, String profile) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.profile = profile;
    }
}
