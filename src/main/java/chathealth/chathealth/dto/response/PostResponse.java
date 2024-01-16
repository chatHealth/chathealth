package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.entity.post.SymptomType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    private List<PicturePost> picturePost;
    private String representativeImg;

    private String company;

    private SymptomType symptom;
    private List<Material> material;

    // yyyy-MM-dd 변환
    private String createdAt;

    private Long count;

    private Integer hitCount;
    private Integer likeCount;
    private Integer reviewCount;

    @Builder
    public PostResponse(Long id, String title, String content, List<PicturePost> picturePost, String representativeImg, String company, SymptomType symptom, List<Material> material, String createdAt, Long count, Integer hitCount, Integer likeCount, Integer reviewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.picturePost = picturePost;
        this.representativeImg = representativeImg;
        this.company = company;
        this.symptom = symptom;
        this.material = material;
        this.createdAt = createdAt;
        this.count = count;
        this.hitCount = hitCount;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
    }
}