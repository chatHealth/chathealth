package chathealth.chathealth.entity.member;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Address {

    private String address;
    private String addressDetail;
    private Integer postcode;
}
