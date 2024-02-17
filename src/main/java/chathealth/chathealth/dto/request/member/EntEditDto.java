

package chathealth.chathealth.dto.request.member;

import chathealth.chathealth.entity.member.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EntEditDto {
    private Long id;

    @NotBlank(message = "법인명은 필수입니다.")
    @Size(max=50, message = "상호명은 50자 이내로 작성해주세요.")
    private String company;
    @NotBlank(message = "대표자명은 필수입니다.")
    private String ceo;
    @NotBlank(message = "주소는 필수입니다.")
    private String frontAddress;
    private String addressDetail;
    private String postcode;
    private Address address;


}
