package chathealth.chathealth.dto.response.message;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReCommnetSelectDto {
    private Long id;
    private String profile;
    private String nickName;
    private String name;

    private String content;

    private String createDate;
    private boolean checkUser;
}
