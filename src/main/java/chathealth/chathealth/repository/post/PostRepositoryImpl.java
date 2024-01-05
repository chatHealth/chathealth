package chathealth.chathealth.repository.post;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.SymptomType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static chathealth.chathealth.entity.post.QMaterial.material;
import static chathealth.chathealth.entity.post.QMaterialPost.materialPost;
import static chathealth.chathealth.entity.post.QPost.post;
import static chathealth.chathealth.entity.post.QSymptom.symptom;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Post> getPosts(PostSearch postSearch) {
        return queryFactory.selectFrom(post)
                .where(post.deletedDate.isNull(),
                        titleContains(postSearch.getTitle()),
                        symptomTypeEq(postSearch.getSymptomType()),
                        materialIn(postSearch.getMaterialName())
                        )
                .leftJoin(post.symptom, symptom)
                .fetchJoin()
                .rightJoin(materialPost).on(materialPost.post.eq(post))
                .fetchJoin()
                .leftJoin(materialPost).on(materialPost.material.eq(material))
                .fetchJoin()
                .orderBy(post.createdDate.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
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
        if (title == null) {
            return null;
        }
        return post.title.contains(title);
    }
}
