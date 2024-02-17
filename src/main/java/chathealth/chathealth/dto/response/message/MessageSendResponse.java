package chathealth.chathealth.dto.response.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageSendResponse {

    private Long id;
    private long receiverId;
    private String receiverNickname;
    private String title;
    private LocalDateTime sendDate;
    private boolean isRead;

    @Builder
    public MessageSendResponse(Long id, Long receiverId, String receiverNickname, String title, LocalDateTime sendDate, Integer isRead) {
        this.id = id;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.title = title;
        this.sendDate = sendDate;
        this.isRead = isRead == 1;
    }

    public static MessageSendResponse get(Message message, Users receiver) {
        return MessageSendResponse.builder()
                .id(message.getId())
                .title(message.getTitle())
                .receiverId(receiver.getId())
                .receiverNickname(receiver.getNickname() == null ? receiver.getName() : receiver.getNickname())
                .isRead(message.getIsRead())
                .sendDate(message.getCreatedDate())
                .build();
    }
}