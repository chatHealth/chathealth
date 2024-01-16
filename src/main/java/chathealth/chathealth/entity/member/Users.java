package chathealth.chathealth.entity.member;

import chathealth.chathealth.constants.Grade;
import chathealth.chathealth.dto.request.UserEditDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

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

    public void update(UserEditDto userEditDto) {
        if(userEditDto.getNickname() != null) this.nickname = userEditDto.getNickname();
        super.update(userEditDto.getAddress());
    }
}
