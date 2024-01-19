package chathealth.chathealth.repository;

import chathealth.chathealth.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReViewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllById(long id);
}
