package chathealth.chathealth.dto.request;


import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
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
    private String content;
    private LocalDateTime CreatedDate;
}
