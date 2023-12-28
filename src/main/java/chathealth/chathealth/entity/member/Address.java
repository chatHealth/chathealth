package chathealth.chathealth.entity.member;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String address;
    private String addressDetail;
    private Integer postcode;
}
