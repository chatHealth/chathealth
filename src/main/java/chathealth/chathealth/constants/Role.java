package chathealth.chathealth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    WAITING_ENT("ROLE_WAITING_ENT"),
    PERMITTED_ENT("ROLE_PERMITTED_ENT"),
    REJECTED_ENT("ROLE_REJECTED_ENT"),
    ADMIN("ROLE_ADMIN,ROLE_USER,ROLE_PERMITTED_ENT");
    private final String role;
}
