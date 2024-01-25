package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomInner {

    private Long id;

    private String name;
    private String image;

    private Long senderId;

//    private Long representativeMemberId;

    private String description;

    private int userCount;

    private boolean isRepresentative;

    @Builder
    public ChatRoomInner(Long id, String name, String image, Long senderId, String description, int userCount, boolean isRepresentative) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.senderId = senderId;
        this.description = description;
        this.userCount = userCount;
        this.isRepresentative = isRepresentative;
    }
}
