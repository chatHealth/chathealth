package chathealth.chathealth.repository;

import chathealth.chathealth.entity.borad.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom{

}