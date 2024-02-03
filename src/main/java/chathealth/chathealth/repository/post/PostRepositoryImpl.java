package chathealth.chathealth.repository.post;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.SymptomType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static chathealth.chathealth.entity.post.QMaterial.material;
import static chathealth.chathealth.entity.post.QPost.post;
import static chathealth.chathealth.entity.post.QPostHit.postHit;
import static chathealth.chathealth.entity.post.QSymptom.symptom;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> getPosts(PostSearch postSearch) {
        return queryFactory.select(post)
                .from(post)
                .where(post.deletedDate.isNull(),
                        titleContains(postSearch.getTitle()),
                        companyContains(postSearch.getCompany()),
                        symptomTypeEq(postSearch.getSymptomType()),
                        materialIn(postSearch.getMaterialName())
                )
                .innerJoin(post.member)
                .fetchJoin()
                .leftJoin(post.symptom)
                .fetchJoin()
                .leftJoin(post.materialList)
                .fetchJoin()
                .leftJoin(post.postHitList)
                .leftJoin(post.postLikeList)
                .leftJoin(post.reviewList)
                .leftJoin(post.postImgList)
                .orderBy(getOrderSpecifier(postSearch))
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();
    }

    @Override
    public Long getPostsCount(PostSearch postSearch) {
        return queryFactory
                .select(post.countDistinct())
                .from(post)
                .where(post.deletedDate.isNull(),
                        titleContains(postSearch.getTitle()),
                        symptomTypeEq(postSearch.getSymptomType()),
                        materialIn(postSearch.getMaterialName())
                )
                .leftJoin(post.symptom, symptom)
                .leftJoin(post.materialList)
                .fetchOne();
    }

    @Override
    public List<Post> getBestPostsPerDay(){
        return queryFactory.selectFrom(post)
                .where(post.deletedDate.isNull(),
                        postHit.createdDate.between(LocalDateTime.now().minusDays(1), LocalDateTime.now()))
                .leftJoin(postHit).on(postHit.post.eq(post))
                .orderBy(postHit.count().desc())
                .groupBy(post)
                .limit(5)
                .fetch();
    }

    @Override
    public List<Post> getBestPostsPerWeek() {
        return queryFactory.selectFrom(post)
                .where(post.deletedDate.isNull(),
                        postHit.createdDate.between(LocalDateTime.now().minusWeeks(1), LocalDateTime.now()))
                .leftJoin(postHit).on(postHit.post.eq(post))
                .orderBy(postHit.count().desc())
                .groupBy(post)
                .limit(5)
                .fetch();
    }

    @Override
    public List<Post> getRecentPosts(Member member) {
        return queryFactory.selectFrom(post)
                .where(post.deletedDate.isNull(),
                        postHit.member.eq(member))
                .leftJoin(postHit).on(postHit.post.eq(post))
                .orderBy(postHit.modifiedDate.desc())
                .limit(5)
                .fetch();
    }

    private static BooleanExpression materialIn(List<String> materialName) {
        if (materialName == null || materialName.isEmpty()) {
            return null;
        }
        return material.materialName.in(materialName);
    }

    private static BooleanExpression symptomTypeEq(SymptomType symptomType) {
        if (symptomType == null) {
            return null;
        }
        return symptom.symptomName.eq(symptomType);
    }

    private static BooleanExpression titleContains(String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        return post.title.contains(title);
    }

    private static BooleanExpression companyContains(String company) {
        if(company == null || company.isEmpty()){
            return null;
        }
        return post.member.company.contains(company);
    }

    private static OrderSpecifier<?> getOrderSpecifier(PostSearch postSearch) {
        switch (postSearch.getOrdercondition()){
            case RECENT -> {
                return post.createdDate.desc();
            }
            case OLD -> {
                return post.createdDate.asc();
            }
            case HIT -> {
                return post.postHitList.size().desc();
            }
            case LIKE -> {
                return post.postLikeList.size().desc();
            }
            case REVIEW -> {
                return post.reviewList.size().desc();
            }
        }
        return post.createdDate.desc();
    }
}