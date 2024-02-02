package chathealth.chathealth.repository;

import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ReViewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.member WHERE r.post = :post")
    Page<Review> findAllByPost(Post post, Pageable pageable);

    List<Review> findAllById(long id);
    List<Review> findByMemberId(Long id);
    Integer countByPostIdAndDeletedDateIsNull(long id);

    @Query("SELECT AVG(s.score) FROM Review s WHERE s.post.id = :postId AND s.deletedDate IS NULL")
    Double findAverageScoreByPostIdAndDeletedDateIsNull(long postId);

}
