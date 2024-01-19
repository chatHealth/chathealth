package chathealth.chathealth.dto.response.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MessageReceiveResponse {

    private Long id;
    private long senderId;
    private String senderNickname;
    private String title;
    private String sendDate;
    private boolean isRead;

    @Builder
    public MessageReceiveResponse(Long id, Long senderId, String senderNickname, String title, LocalDateTime sendDate, Integer isRead) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.title = title;
        this.sendDate = sendDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.isRead = isRead != 1;
    }

    public static MessageReceiveResponse get(Message message, Users sender) {
        return MessageReceiveResponse.builder()
                .id(message.getId())
                .title(message.getTitle())
                .senderId(sender.getId())
                .senderNickname(sender.getNickname())
                .isRead(message.getIsRead())
                .sendDate(message.getCreatedDate())
                .build();
    }
}
