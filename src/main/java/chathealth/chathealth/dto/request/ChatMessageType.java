package chathealth.chathealth.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChatMessageType {

    ENTER("입장", "님이 입장하셨습니다."),
    TALK("일반 채팅", ""),
    QUIT("퇴장", "님이 퇴장하셨습니다.");

    private final String description;
    private final String message;
}
