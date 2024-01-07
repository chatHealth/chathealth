package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.PicturePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicturePostRepository extends JpaRepository<PicturePost, Long> {

    public List<PicturePost> findAllByPostIdOrderByOrders(Long postId);
}
