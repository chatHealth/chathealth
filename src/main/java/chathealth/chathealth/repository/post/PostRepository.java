package chathealth.chathealth.repository.post;

import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// @repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findByMemberId(Long id);
    List<Post> findByMaterialListMaterialIdIn(List<Long> materialIds);
}

