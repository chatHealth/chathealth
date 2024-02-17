package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecentViewPost {

    private Long id;

    private String title;

    private String representativeImg;

    @Builder
    public RecentViewPost(Long id, String title, String representativeImg) {
        this.id = id;
        this.title = title;
        this.representativeImg = representativeImg;
    }
}
