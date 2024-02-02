package chathealth.chathealth.dto.response.message;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReCommnetSelectDto {
    private Long id;
    private Long memberId;
    private String profile;
    private String name;

    private String nickName;
    private String company;

    private String content;

    private LocalDateTime createDate;
    private boolean checkUser;
}
