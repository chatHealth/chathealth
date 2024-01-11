package chathealth.chathealth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 유저"),
    WAITING_ENT("승인 대기중"),
    PERMITTED_ENT("승인 완료"),
    REJECTED_ENT("승인 거절"),
    ADMIN("관리자");
    private final String role;
}
