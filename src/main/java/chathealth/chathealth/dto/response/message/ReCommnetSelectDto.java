package chathealth.chathealth.dto.response.message;

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
public class ReCommnetSelectDto {
    private Long id;
    private String profile;
    private String nickName;

    private String content;

    private String createDate;
    private boolean checkUser;
}
