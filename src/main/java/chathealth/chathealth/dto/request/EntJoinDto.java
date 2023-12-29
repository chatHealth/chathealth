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
public class EntJoinDto {
    private Long id;
    private String pw;
    private String email;
    private Address address;
    private String company;
    private String ceo;
    private LocalDate entNo;
    private String profile;
    private Role role;
    private LocalDateTime deletedDate;
    private int report;
}
