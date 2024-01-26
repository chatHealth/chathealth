package chathealth.chathealth.repository;


import chathealth.chathealth.dto.response.ReViewSelectDto;
import chathealth.chathealth.entity.ReComment;
import chathealth.chathealth.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment,Long> {
    @Query("SELECT r FROM ReComment r LEFT JOIN FETCH r.member WHERE r.review = :review")
    List<ReComment> findAllByReview(Review review);
}
