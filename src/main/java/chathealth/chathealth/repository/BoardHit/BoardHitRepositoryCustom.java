package chathealth.chathealth.repository.BoardHit;

import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.member.Member;

public interface BoardHitRepositoryCustom {
    // 12시간 내에 조회한 적 있는지 유무 확인
    boolean existsHit(Board board, Member member);
}
