package chathealth.chathealth.dto.response;

import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.entity.post.SymptomType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    private List<PicturePost> picturePost;
    private String representativeImg;

    private String company;

    private SymptomType symptom;
    private List<Material> material;

    // yyyy-MM-dd HH:mm:ss 변환
    private String createdDate;

    // yyyy-MM-dd 변환
    private String createdAt;

    private Long count;

    private Long hitCount;
    private Long likeCount;
    private Long reviewCount;

}