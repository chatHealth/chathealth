package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.constants.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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
    private Integer postcode;
    private String frontAddress;
    private String addressDetail;
    private String company;
    private String ceo;
    private String entNo;
    private MultipartFile profile;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDate birth;

}