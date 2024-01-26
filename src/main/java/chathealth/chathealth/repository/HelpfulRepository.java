package chathealth.chathealth.repository;

import chathealth.chathealth.entity.Helpful;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpfulRepository extends JpaRepository<Helpful,Long> {


    Helpful findByMemberIdAndReviewId(Long memberId,Long reviewId);
    void deleteByMemberIdAndReviewId(Long memberId,Long reviewId);
    Long countByReviewId(Long num);
}
