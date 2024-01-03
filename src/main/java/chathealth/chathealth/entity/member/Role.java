package chathealth.chathealth.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("role_user"),
    WAITING_ENT("role_waiting_ent"),
    PERMITTED_ENT("role_permitted_ent"),
    REJECTED_ENT("role_rejected_ent"),
    ADMIN("role_admin"),
    ANONYMOUS("role_anonymous");
    private final String role;
}
