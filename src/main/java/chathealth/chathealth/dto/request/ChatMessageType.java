package chathealth.chathealth.dto.request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatMessageType {

    ENTER("입장"),
    TALK("일반 채팅"),
    QUIT("퇴장");

    private final String description;
}
