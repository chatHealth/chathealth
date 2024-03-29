package chathealth.chathealth.dto.response.member;

import chathealth.chathealth.constants.Grade;
import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.member.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private Role role;
}
