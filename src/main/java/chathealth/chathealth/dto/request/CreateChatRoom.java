package chathealth.chathealth.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateChatRoom {

    @Size(min = 1, max = 20, message = "방 이름은 1자 이상 20자 이하로 입력해주세요.")
    private String name;

    @Size(min = 1, max = 100, message = "방 설명은 1자 이상 100자 이하로 입력해주세요.")
    private String description;

    @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
    private String nickname;
}
