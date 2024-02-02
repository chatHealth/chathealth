package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResponse {

    private String nickname;

    private String message;

    private LocalDateTime timestamp;

    private Long senderId;

    private boolean isMine;

    @Builder
    public ChatMessageResponse(String nickname, String message, LocalDateTime timestamp,Long senderId, boolean isMine) {
        this.nickname = nickname;
        this.message = message;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.isMine = isMine;
    }
}