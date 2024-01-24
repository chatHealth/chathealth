package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomInner {

    private Long id;

    private String name;
    private String image;

    private Long senderId;

    private Long representativeMemberId;

    private String description;

    private int userCount;

    @Builder
    public ChatRoomInner(Long id, String name, String image, Long senderId, Long representativeMemberId, String description, int userCount) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.senderId = senderId;
        this.representativeMemberId = representativeMemberId;
        this.description = description;
        this.userCount = userCount;
    }
}
