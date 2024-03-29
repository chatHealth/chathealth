package chathealth.chathealth.service;

import chathealth.chathealth.controller.PostRestController;
import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.MaterialSymptomDto;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.entity.post.*;
import chathealth.chathealth.repository.*;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.post.PostRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static chathealth.chathealth.dto.request.OrderCondition.RECENT;
import static chathealth.chathealth.entity.post.SymptomType.INTESTINE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

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
    @Autowired
    PostService postService;
    @Autowired
    PicturePostRepository picturePostRepository;
    @Autowired
    PostHitRepository postHitRepository;
    @Autowired
    PostRestController postHitService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("포스트 목록 조회")
//    @Rollback(value = false)
    public void getPosts() throws Exception{
        //given
        PostSearch postSearch = PostSearch.builder()
                .title("입니다")
                .company("중앙컴퍼니")
                .symptomType(INTESTINE)
                .materialName(List.of("프로바이오틱스"))
                .ordercondition(RECENT)
                .size(20)
                .build();

        Ent ent = Ent.builder()
                .company("중앙컴퍼니")
                .build();
        Ent savedEnt = memberRepository.save(ent);

        Symptom symptom = Symptom.builder()
                .symptomName(INTESTINE)
                .build();
        Symptom savedSymptom = symptomRepository.save(symptom);

        Material material = Material.builder()
                .materialName("프로바이오틱스")
                .build();
        Material savedMaterial = materialRepository.save(material);

        Post post = Post.builder()
                .title("제목입니다")
                .symptom(savedSymptom)
                .member(savedEnt)
                .build();
        Post savedPost = postRepository.save(post);

        MaterialPost materialPost = MaterialPost.builder()
                .material(savedMaterial)
                .post(savedPost)
                .build();

        materialPostRepository.save(materialPost);

        PicturePost picture1 = picturePostRepository.save(PicturePost.builder()
                .pictureUrl("이미지유알엘1")
                .post(savedPost)
                .orders(0)
                .build());

        PicturePost picture2 = picturePostRepository.save(PicturePost.builder()
                .pictureUrl("이미지 유알엘2")
                .post(savedPost)
                .orders(1)
                .build());

        List<PicturePost> pictures = new ArrayList<>();
        pictures.add(picture1);
        pictures.add(picture2);

        picturePostRepository.saveAll(pictures);

        for (int i = 0; i < 89; i++) {

            Post post1 = Post.builder()
                    .title("제목입니다")
                    .symptom(savedSymptom)
                    .member(savedEnt)
                    .build();
            Post savedPost1 = postRepository.save(post1);

            MaterialPost materialPost1 = MaterialPost.builder()
                    .material(savedMaterial)
                    .post(savedPost1)
                    .build();

            MaterialPost savedMaterial1 = materialPostRepository.save(materialPost1);

            PicturePost picture4 = picturePostRepository.save(PicturePost.builder()
                    .pictureUrl("이미지3")
                    .post(savedPost1)
                    .orders(1)
                    .build());

            PicturePost picture3 = picturePostRepository.save(PicturePost.builder()
                    .pictureUrl("이미지 유알엘2")
                    .post(savedPost1)
                    .orders(2)
                    .build());
            List<PicturePost> pictures1 = new ArrayList<>();
            pictures1.add(picture3);
            pictures1.add(picture4);
            picturePostRepository.saveAll(pictures1);

            postHitRepository.save(PostHit.builder()
                    .post(savedPost1)
                    .build());
            postHitRepository.save(PostHit.builder()
                    .post(savedPost1)
                    .build());
        }
        em.flush();
        em.clear();

        //when
        List<PostResponse> posts = postService.getPosts(postSearch);

        //then
        assertThat(posts.size()).isEqualTo(20);
        assertThat(posts.get(0).getRepresentativeImg()).isEqualTo("이미지3");
//        assertThat(posts.get(0).getCount()).isEqualTo(90);

    }

    @Test
    @DisplayName("포스트 목록 조회시 데이터가 없으면 빈 리스트를 반환한다")
//    @Rollback(value = false)
    public void getEmptyPosts() throws Exception{
        //given
        PostSearch postSearch = PostSearch.builder()
                .title("제목입니다")
                .company("회사입니다")
                .symptomType(INTESTINE)
                .materialName(List.of("아스피린아님", "타이레놀"))
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
        List<PostResponse> posts = postService.getPosts(postSearch);

        //then
        assertThat(posts.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("일간 베스트 포스트 조회")
    public void getBestPostPerDay() {
        //given


        Ent ent = Ent.builder()
                .company("회사이름")
                .build();
        Ent savedEnt = memberRepository.save(ent);

        for(int i = 0; i < 10; i++){
            Users user = Users.builder()
                    .name("이름" + i)
                    .build();
            Users savedUser = memberRepository.save(user);

            Post post = Post.builder()
                    .member(savedEnt)
                    .title("제목" + i)
                    .build();
            Post savedPost = postRepository.save(post);

            PostHit postHit = PostHit.builder()
                    .post(savedPost)
                    .member(savedUser)
                    .build();
            postHitRepository.save(postHit);
        }
        for(int i = 10; i < 14; i++){
            Users user = Users.builder()
                    .name("이름" + i)
                    .build();
            Users savedUser = memberRepository.save(user);

            Post post = Post.builder()
                    .member(savedEnt)
                    .title("제목" + i)
                    .build();
            Post savedPost = postRepository.save(post);

            for(int j =0; j<2;j++) {
                PostHit postHit = PostHit.builder()
                        .post(savedPost)
                        .member(savedUser)
                        .build();
                postHitRepository.save(postHit);}
        }
        List<PostResponse> bestPostsPerDay = postHitService.getBestPostsPerDay();
        assertThat(bestPostsPerDay.size()).isEqualTo(5);
    }

    @DisplayName("MaterialSymptomDto 테스트")
    @Test
    public void test1() throws Exception{
        //when
        List<MaterialSymptomDto> materialBySymptomType = postService.getMaterialBySymptomType();
        //then
        assertThat(materialBySymptomType.size()).isEqualTo(10);
    }
}
