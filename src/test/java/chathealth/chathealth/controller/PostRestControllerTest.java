package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.post.*;
import chathealth.chathealth.repository.*;
import chathealth.chathealth.repository.post.PostRepository;
import chathealth.chathealth.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static chathealth.chathealth.dto.request.OrderCondition.RECENT;
import static chathealth.chathealth.entity.post.SymptomType.INTESTINE;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostRestControllerTest {

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
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
//    @Rollback(value = false)
    public void getPosts() throws Exception {
        //given
        PostSearch postSearch = PostSearch.builder()
                .title("입니다")
                .company("중앙컴퍼니")
                .symptomType(INTESTINE)
                .materialName(List.of("아스피린", "타이레놀"))
                .ordercondition(RECENT)
                .size(20)
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

        PicturePost picture1 = picturePostRepository.save(PicturePost.builder()
                .pictureUrl("이미지유알엘1")
                .post(post)
                .orders(0)
                .build());

        PicturePost picture2 = picturePostRepository.save(PicturePost.builder()
                .pictureUrl("이미지 유알엘2")
                .post(post)
                .orders(1)
                .build());

        List<PicturePost> pictures = new ArrayList<>();
        pictures.add(picture1);
        pictures.add(picture2);

        picturePostRepository.saveAll(pictures);

        for (int i = 0; i < 89; i++) {

            Post post1 = Post.builder()
                    .title("제목입니다")
                    .symptom(symptom)
                    .member(ent)
                    .build();
            postRepository.save(post1);

            MaterialPost materialPost1 = MaterialPost.builder()
                    .material(material)
                    .post(post1)
                    .build();

            materialPostRepository.save(materialPost1);

            PicturePost picture4 = picturePostRepository.save(PicturePost.builder()
                    .pictureUrl("이미지유알엘3")
                    .post(post1)
                    .orders(0)
                    .build());

            PicturePost picture3 = picturePostRepository.save(PicturePost.builder()
                    .pictureUrl("이미지 유알엘2")
                    .post(post1)
                    .orders(1)
                    .build());
            List<PicturePost> pictures1 = new ArrayList<>();
            pictures1.add(picture3);
            pictures1.add(picture4);
            picturePostRepository.saveAll(pictures1);

            postHitRepository.save(PostHit.builder()
                    .post(post1)
                    .build());
            postHitRepository.save(PostHit.builder()
                    .post(post1)
                    .build());
        }
        //expected
        mockMvc.perform(get("/post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postSearch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(20)))
                .andExpect(jsonPath("$.[0].title", is("제목입니다")))
                .andExpect(jsonPath("$.[0].representativeImg", is("이미지유알엘3")))
                .andExpect(jsonPath("$.[0].symptom", is("INTESTINE")))
                .andExpect(jsonPath("$.[0].count", is(90)))
                .andDo(print());
    }

}