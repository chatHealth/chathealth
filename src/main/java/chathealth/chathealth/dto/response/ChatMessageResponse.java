package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessageResponse {

    private String nickname;

    private String message;

    private String timestamp;

    private Long senderId;

    @Builder
    public ChatMessageResponse(String nickname, String message, String timestamp,Long senderId) {
        this.nickname = nickname;
        this.message = message;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
}