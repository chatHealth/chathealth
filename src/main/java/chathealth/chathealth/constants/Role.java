package chathealth.chathealth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("일반 유저"),
    ROLE_WAITING_ENT("승인 대기중"),
    ROLE_PERMITTED_ENT("승인 완료"),
    ROLE_REJECTED_ENT("승인 거절"),
    ROLE_ADMIN("관리자");
    private final String role;

    public static Set<Role> getEntRoles() {
        return new HashSet<>() {{
            add(ROLE_WAITING_ENT);
            add(ROLE_PERMITTED_ENT);
            add(ROLE_REJECTED_ENT);
        }};
    }
}
