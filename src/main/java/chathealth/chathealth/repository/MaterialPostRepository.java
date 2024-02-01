package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialPostRepository extends JpaRepository<MaterialPost, Long> {
    List<MaterialPost> findAllByPost(Post id);
    List<MaterialPost> deleteAllByPost(Post id);
    List<Material> findAllMaterialByPost(Post post);
//    List<Material> findMaterialByPost(Post post);

}
