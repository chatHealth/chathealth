package chathealth.chathealth.dto.response.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Users;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageSendResponseDetail {

    private long receiverId;
    private String receiverNickname;
    private String title;
    private String content;
    private String sendDate;
    private boolean isRead;

    @Builder
    public MessageSendResponseDetail(String content, Long receiverId, String receiverNickname, String title, LocalDateTime sendDate, Integer isRead) {
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.title = title;
        this.sendDate = sendDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.isRead = isRead != 1;
    }

    public static MessageSendResponseDetail get(Message message, Users receiver) {
        return MessageSendResponseDetail.builder()
                .receiverId(receiver.getId())
                .receiverNickname(receiver.getNickname())
                .title(message.getTitle())
                .sendDate(message.getCreatedDate())
                .isRead(message.getIsRead())
                .build();
    }
}
