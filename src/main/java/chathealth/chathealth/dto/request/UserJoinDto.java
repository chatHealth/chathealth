package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class UserJoinDto {
    private Long id;
    private String pw;
    private String email;
    private Address address;
    private Integer postcode;
    private String frontAddress;
    private String addressDetail;
    private String name;
    private String nickname;
    private LocalDate birth;
    private String profile;
    private Role role;
    private Grade grade;
    private LocalDateTime deletedDate;
    private int report;
}
