package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageDto {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 200, message = "200자 이내로 입력해주세요.")
    private String content;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50, message = "50자 이내로 입력해주세요.")
    private String title;

    public SendMessageDto(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public Message toEntity(Member sender, Member receiver) {
        return Message.builder()
                .isRead(0)
                .content(content)
                .sender(sender)
                .receiver(receiver)
                .title(title)
                .build();
    }
}
