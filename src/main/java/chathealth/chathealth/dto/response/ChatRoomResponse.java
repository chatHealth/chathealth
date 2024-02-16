package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

    private Long id;
    private String name;
    private String image;
    private String description;
    private int userCount;
    private boolean isJoined;

    @Builder
    public ChatRoomResponse(Long id, String name, String image, String description, int userCount, boolean isJoined) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.userCount = userCount;
        this.isJoined = isJoined;
    }
}