package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.PicturePost;
import groovy.lang.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PicturePostRepository extends JpaRepository<PicturePost, Long> {

    public List<PicturePost> findAllByPostIdOrderByOrders(Long postId);
    public List<PicturePost> findAllByPostId(Long postId);
    public List<PicturePost> findAllByPostIdAndOrders(Long postId,Integer order);
    public String findByPostIdAndOrders(long postId,Integer order);
}
