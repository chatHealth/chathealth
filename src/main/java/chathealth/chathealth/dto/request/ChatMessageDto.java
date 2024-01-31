package chathealth.chathealth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageDto {

    @NotBlank(message = "메시지를 입력해주세요.")
    @Size(max = 1000, message = "채팅은 1000자 이하로 입력해주세요.")
    private String content;

    private String senderEmail;
    private String nickname;
    private Long roomId;
    private Long senderId;

    private ChatMessageType type;

}
