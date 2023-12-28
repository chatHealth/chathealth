package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class UserInfoDto {
    private Long id;
    private String email;
    private Address address;
    private String name;
    private String nickname;
    private LocalDate birth;
    private String profile;
    private Grade grade;
    private LocalDateTime deletedDate;
}
