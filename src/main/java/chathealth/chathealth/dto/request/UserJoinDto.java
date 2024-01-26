package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.constants.Grade;
import chathealth.chathealth.constants.Role;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class UserJoinDto {
    private Long id;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",message = "비밀번호는 8~15자리이고 영문, 숫자, 기호를 포함하고 있어야 합니다.")
    private String pw;
    private String email;
    private Address address;
    private String postcode;
    private String frontAddress;
    private String addressDetail;
    private String name;
    private String nickname;
    private LocalDate birth;
    private MultipartFile profile;
    private Role role;
    private Grade grade;
    private LocalDateTime createDate;
}
