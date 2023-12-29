package chathealth.chathealth.entity.entity.member;

import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("U")
public class Users extends Member {
    private String name;
    private String nickname;
    private Grade grade;
}
