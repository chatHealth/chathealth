package chathealth.chathealth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String content;
    private String senderEmail;
    private String nickname;
    private Long roomId;
    private Long senderId;

    private ChatMessageType type;

}
