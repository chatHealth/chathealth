package chathealth.chathealth.dto.request.member;

import chathealth.chathealth.entity.member.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserEditDto {

    private Long id;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max=10, message = "별명은 10자 이내로 작성해주세요.")
    private String nickname;
    @NotBlank(message = "주소는 필수입력사항입니다.")
    private String frontAddress;
    private String addressDetail;
    @NotBlank(message = "우편번호는 필수입력사항입니다.")
    private String postcode;
    private Address address;
}
