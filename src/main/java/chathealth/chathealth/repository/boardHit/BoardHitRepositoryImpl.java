package chathealth.chathealth.repository.boardHit;

import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static chathealth.chathealth.entity.QBoardHit.boardHit;

@RequiredArgsConstructor
public class BoardHitRepositoryImpl implements BoardHitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 12시간 내에 조회한 적 있는지 유무 확인
    @Override
    public boolean existsHit(Board board, Member member) {
        return queryFactory.selectOne()
                .from(boardHit)
                .where(boardHit.board.eq(board),
                        boardHit.member.eq(member),
                        boardHit.createdDate.after(LocalDateTime.now().minusHours(12)))
                .fetchOne() != null;
    }
}
