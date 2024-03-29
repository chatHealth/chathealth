package chathealth.chathealth.constants;

import lombok.Getter;

@Getter
public enum Constants {

    //error
    USER_NOT_FOUND("존재하지 않는 유저입니다."),
    PASSWORD_NOT_EQUAL("비밀번호가 일치하지 않습니다."),
    BOARD_NOT_FOUND("존재하지 않는 게시글 입니다."),
    FORBIDDEN("권한이 없습니다."),
    NOT_EXIST_FILE("존재하지 않는 파일입니다."),
    MESSAGE_NOT_FOUND("존재하지 않는 메세지입니다."),
    UNAUTHORIZED("로그인이 필요합니다."),
    ROOM_NOT_FOUND("존재하지 않는 채팅방입니다."),
  
    //채팅
    ENTER_MESSAGE("님이 입장하셨습니다."),
    QUIT_MESSAGE("님이 퇴장하셨습니다."),
  
    //세션
    EXPIRED_SESSION("세션이 만료되었습니다. 다시 로그인해주세요.");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

}