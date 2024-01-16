package chathealth.chathealth.repository.BoardHit;

import chathealth.chathealth.entity.BoardHit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHitRepository extends JpaRepository<BoardHit, Long>, BoardHitRepositoryCustom{

}
