package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.constants.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class EntInfoDto {
    private Long id;
    private String email;
    private Address address;
    private LocalDate birth;
    private String profile;
    private LocalDateTime deletedDate;
    private Role role;
    private String ceo;
    private String company;
    private String entNo;
}
