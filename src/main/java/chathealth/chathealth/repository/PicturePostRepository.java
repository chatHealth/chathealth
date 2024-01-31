package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicturePostRepository extends JpaRepository<PicturePost, Long> {

    public List<PicturePost> findAllByPostId(Long postId);
    public List<PicturePost> findAllByPostIdAndOrders(Long postId,Integer order);
    public PicturePost findByPostIdAndOrders(long postId,Integer order);
    public void deleteByPost(Post postId);
}
