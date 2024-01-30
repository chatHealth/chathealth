package chathealth.chathealth.repository;

import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReViewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.member WHERE r.post = :post")
    List<Review> findAllByPost(Post post);

    List<Review> findAllById(long id);
    List<Review> findByMemberId(Long id);

}
