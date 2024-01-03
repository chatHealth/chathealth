package chathealth.chathealth.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("E")
public class Ent extends Member{
    private String company;
    private String ceo;
    private String entNo;

    public void update(String ceo, String company,Address address, String password) {
        this.ceo = ceo;
        this.company = company;
        super.update(address, password);
    }
}
