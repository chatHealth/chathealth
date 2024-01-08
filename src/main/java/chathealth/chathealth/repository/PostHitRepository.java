package chathealth.chathealth.repository;

import chathealth.chathealth.entity.post.PostHit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHitRepository extends JpaRepository<PostHit, Long> {
}
