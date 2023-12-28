package chathealth.chathealth.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("U")
public class Users extends Member{
    private String name;
    private String nickname;
    private Grade grade;
}
