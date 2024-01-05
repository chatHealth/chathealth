package chathealth.chathealth.repository.post;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.Symptom;
import chathealth.chathealth.repository.MaterialPostRepository;
import chathealth.chathealth.repository.MaterialRepository;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.SymptomRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static chathealth.chathealth.entity.post.SymptomType.INTESTINE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryImplTest {

    @Autowired
    EntityManager em;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MaterialPostRepository materialPostRepository;
    @Autowired
    SymptomRepository symptomRepository;
    @Autowired
    MaterialRepository materialRepository;

    @Test
    @DisplayName("포스트 조회 조건이 없으면 싹 다 조회함")
    @Rollback(value = false)
    void searchPosts() {
        //given
        PostSearch postSearch = PostSearch.builder()
                .title("제목입니다")
                .company("회사입니다")
                .symptomType(INTESTINE)
                .materialName(List.of("아스피린", "타이레놀"))
                .build();

        Ent ent = Ent.builder()
                .company("중앙컴퍼니")
                .build();
        memberRepository.save(ent);

        Symptom symptom = Symptom.builder()
                .symptomName(INTESTINE)
                .build();
        symptomRepository.save(symptom);

        Material material = Material.builder()
                .materialName("아스피린")
                .build();
        materialRepository.save(material);

        Post post = Post.builder()
                .title("제목입니다")
                .symptom(symptom)
                .member(ent)
                .build();

        postRepository.save(post);

        MaterialPost materialPost = MaterialPost.builder()
                .material(material)
                .post(post)
                .build();

        materialPostRepository.save(materialPost);

        //when
        List<Post> posts = postRepository.getPosts(postSearch);
        //then
//        System.out.println("posts = " + posts);
    }

    @Test
    @DisplayName("포스트 카운트 조회")
    void getPostsCount() throws Exception{
        //given
        PostSearch postSearch = PostSearch.builder()
                .title("제목입니다")
                .company("회사입니다")
                .symptomType(INTESTINE)
                .materialName(List.of("아스피린", "타이레놀"))
                .build();

        Ent ent = Ent.builder()
                .company("중앙컴퍼니")
                .build();
        memberRepository.save(ent);

        Symptom symptom = Symptom.builder()
                .symptomName(INTESTINE)
                .build();
        symptomRepository.save(symptom);

        Material material = Material.builder()
                .materialName("아스피린")
                .build();
        materialRepository.save(material);

        Post post = Post.builder()
                .title("제목입니다")
                .symptom(symptom)
                .member(ent)
                .build();

        postRepository.save(post);

        MaterialPost materialPost = MaterialPost.builder()
                .material(material)
                .post(post)
                .build();

        materialPostRepository.save(materialPost);

        //when
        Long postsCount = postRepository.getPostsCount(postSearch);
        //then
        assertThat(postsCount).isEqualTo(1L);
    }
}