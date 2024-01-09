package chathealth.chathealth.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Address {

    private String address;
    private String addressDetail;
    private String postcode;
}
