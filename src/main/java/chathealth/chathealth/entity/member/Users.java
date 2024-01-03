package chathealth.chathealth.entity.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("U")
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class Users extends Member{
    private String name;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Grade grade;

    public void update(String nickname, Address address, String password) {
        this.nickname = nickname;
        super.update(address, password);
    }
}
