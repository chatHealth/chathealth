package chathealth.chathealth.repository;

import chathealth.chathealth.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReViewRepository extends JpaRepository<Review,Long> {
}
