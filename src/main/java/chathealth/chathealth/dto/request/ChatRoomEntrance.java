package chathealth.chathealth.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChatRoomEntrance {

    @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
    private String nickname;

    private Long roomId;
}
