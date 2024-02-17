package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentWriteDto {
    private Review review;
    private Member member;
    @Size(max=200, message = "내용은 200자 이내로 작성해주세요.")
    private String content;
    private LocalDateTime CreatedDate;
}
