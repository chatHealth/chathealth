package chathealth.chathealth.constants;

import lombok.Getter;

@Getter
public enum Constants {
    USER_NOT_FOUND("존재하지 않는 유저입니다."),
    PASSWORD_NOT_EQUAL("비밀번호가 일치하지 않습니다."),
    BOARD_NOT_FOUND("존재하지 않는 게시글 입니다."),
    FORBIDDEN("권한이 없습니다.");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

}
