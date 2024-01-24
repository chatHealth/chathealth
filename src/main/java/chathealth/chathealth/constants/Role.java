package chathealth.chathealth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("유저"),
    ROLE_WAITING_ENT("승인 대기중 사업자"),
    ROLE_PERMITTED_ENT("승인 완료 사업자"),
    ROLE_REJECTED_ENT("승인 거절 사업자"),
    ROLE_ADMIN("관리자");
    private final String role;

    public static Set<Role> getEntRoles() {
        return Set.of(ROLE_WAITING_ENT, ROLE_PERMITTED_ENT, ROLE_REJECTED_ENT);
    }
}
