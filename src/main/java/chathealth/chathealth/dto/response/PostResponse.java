package chathealth.chathealth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Setter
public class PostResponse {

    private Long id;
    private String title;

    private String representativeImg;

    private String company;

    private LocalDateTime createdAt;

    private Long count;

    private Integer hitCount;
    private Integer likeCount;
    private Integer reviewCount;

    @Builder
    public PostResponse(Long id, String title, String representativeImg, String company, LocalDateTime createdAt, Long count, Integer hitCount, Integer likeCount, Integer reviewCount) {
        this.id = id;
        this.title = title;
        this.representativeImg = representativeImg;
        this.company = company;
        this.createdAt = createdAt;
        this.count = count;
        this.hitCount = hitCount;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
    }
}