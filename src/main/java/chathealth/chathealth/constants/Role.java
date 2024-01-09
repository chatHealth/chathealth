package chathealth.chathealth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("role_user"),
    WAITING_ENT("role_waiting_ent"),
    PERMITTED_ENT("role_permitted_ent"),
    REJECTED_ENT("role_rejected_ent"),
    ADMIN("role_admin");
    private final String role;
}
