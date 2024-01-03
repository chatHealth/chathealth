package chathealth.chathealth.entity.member;

import chathealth.chathealth.dto.request.UserEditDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.*;

@Entity
@Getter
@DiscriminatorValue("U")
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class Users extends Member{
    private String name;
    private String nickname;
    private Grade grade;

    public void update(UserEditDto userEditDto) {
        if(userEditDto.getNickname() != null) this.nickname = userEditDto.getNickname();
        super.update(userEditDto.getAddress());
    }
}
