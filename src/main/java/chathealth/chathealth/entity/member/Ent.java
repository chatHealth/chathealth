package chathealth.chathealth.entity.member;

import chathealth.chathealth.dto.request.member.EntEditDto;
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

    public void update(EntEditDto entEditDto) {
        if(entEditDto.getCeo() != null) this.ceo = entEditDto.getCeo();
        if(entEditDto.getCompany()!=null) this.company = entEditDto.getCompany();
        super.update(entEditDto.getAddress());
    }
}
