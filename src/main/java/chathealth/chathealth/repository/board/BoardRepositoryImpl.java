package chathealth.chathealth.repository.board;

import chathealth.chathealth.dto.request.BoardSearchDto;
import chathealth.chathealth.entity.board.Board;
import chathealth.chathealth.entity.board.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static chathealth.chathealth.entity.board.QBoard.board;
import static chathealth.chathealth.entity.member.QUsers.users;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> getBoards(BoardSearchDto boardSearchDto){
        return queryFactory.selectFrom(board)
                .where(categoryEq(boardSearchDto.getCategory()),
                        titleContains(boardSearchDto.getTitle()),
                        contentContains(boardSearchDto.getContent()),
                        writerContains(boardSearchDto.getWriter()),
                        board.deletedDate.isNull())
                .leftJoin(board.user, users)
                .fetchJoin()
                .limit(boardSearchDto.getSize())
                .offset(boardSearchDto.getOffset())
                .orderBy(board.createdDate.desc())
                .fetch();
    }

    @Override
    public Long getBoardCount(BoardSearchDto boardSearchDto) {
        return queryFactory.select(board.count())
                .from(board)
                .where(categoryEq(boardSearchDto.getCategory()),
                        titleContains(boardSearchDto.getTitle()),
                        contentContains(boardSearchDto.getContent()),
                        writerContains(boardSearchDto.getWriter()),
                        board.deletedDate.isNull())
                .fetchOne();
    }

    @Override
    public List<Board> getBoardsByCategoryRecent(Category category){
        return queryFactory.selectFrom(board)
                .where(categoryEq(category),
                        board.deletedDate.isNull())
                .join(board.user, users)
                .fetchJoin()
                .limit(5)
                .orderBy(board.createdDate.desc())
                .fetch();
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : board.category.eq(category);
    }

    private static BooleanExpression contentContains(String content) {
        return hasText(content) ? board.content.contains(content) : null;
    }
    private static BooleanExpression writerContains(String writer) {
        return hasText(writer) ? board.user.nickname.contains(writer) : null;
    }
    private static BooleanExpression titleContains(String title) {
        return hasText(title) ? board.title.contains(title) : null;
    }
}
