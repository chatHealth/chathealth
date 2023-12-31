

package chathealth.chathealth.dto.request;

import chathealth.chathealth.entity.member.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntEditDto {
    private Long id;

    @NotBlank(message = "대표자명은 필수입니다.")
    private String ceo;

//    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
//    private String pw;
//
//    @NotBlank(message = "새 비밀번호를 입력해주세요.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$", message = "비밀번호는 영문, 숫자를 포함한 8~20자리여야 합니다.")
//    private String newPw;
//
//    @NotBlank(message = "새 비밀번호를 다시 입력해주세요.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$", message = "비밀번호는 영문, 숫자를 포함한 8~20자리여야 합니다.")
//    private String newPwCheck;
    private Address address;

    private String company;
}
