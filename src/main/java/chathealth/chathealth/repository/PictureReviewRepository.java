package chathealth.chathealth.repository;

import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.post.PicturePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureReviewRepository extends JpaRepository<PictureReView,Long> {
    List<String> findAllById(Long id);
    public List<PicturePost> findAllByReviewId(Long reviewId);
}
