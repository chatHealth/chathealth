package chathealth.chathealth.repository;

import chathealth.chathealth.entity.PictureReView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureReviewRepository extends JpaRepository<PictureReView,Long> {
}
