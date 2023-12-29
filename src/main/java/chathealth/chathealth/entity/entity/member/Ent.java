package chathealth.chathealth.entity.entity.member;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("E")
public class Ent extends Member {
    private String company;
    private String ceo;
    private String entNo;
}
