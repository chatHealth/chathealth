package chathealth.chathealth.dto.response.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MessageReceiveResponseDetail {

    private long senderId;
    private String senderNickname;
    private String title;
    private String sendDate;
    private String content;

    @Builder
    public MessageReceiveResponseDetail(Long senderId, String senderNickname, String title, LocalDateTime sendDate, String content) {
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static MessageReceiveResponseDetail get(Message message, Users sender) {
        return MessageReceiveResponseDetail.builder()
                .senderId(sender.getId())
                .senderNickname(sender.getNickname())
                .title(message.getTitle())
                .sendDate(message.getCreatedDate())
                .content(message.getContent())
                .build();
    }
}