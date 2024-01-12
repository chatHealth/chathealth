package chathealth.chathealth.entity.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
    NOTICE("공지"), FREE("자유"), NEWS("뉴스"), QNA("질문"), TIP("팁"), INFO("정보");

    private final String message;
}
