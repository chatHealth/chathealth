package chathealth.chathealth.service;


import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.request.PostWriteDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.dto.response.MaterialDto;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.dto.response.SymptomDto;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.Symptom;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.*;
import chathealth.chathealth.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PicturePostRepository picturePostRepository;
    private final SymptomRepository symptomRepository;
    private final MaterialRepository materialRepository;
    private final MemberRepository memberRepository;


    // public Post insertPost(){}

    // 포스트 목록 조회
    public List<PostResponse> getPosts(PostSearch postSearch) {

        Long count = postRepository.getPostsCount(postSearch);

        // 페이지가 존재하지 않을 경우 마지막 페이지로 이동
        if (count / postSearch.getSize() < postSearch.getPage()) {
            postSearch.setPage((int) (count / postSearch.getSize()));
        }

        return postRepository.getPosts(postSearch).stream()
                .map(post ->
                        PostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .company(post.getMember().getCompany())
                                .representativeImg(getRepresentativeImg(post))
                                .count(count)
                                .createdDate(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .hitCount(post.getPostHitCount())
                                .likeCount(post.getPostLikeCount())
                                .reviewCount(post.getReviewCount())
                                .symptom(post.getSymptom().getSymptomName())
                                .build())
                .toList();
    }

    private String getRepresentativeImg(Post post) {
        return picturePostRepository.findAllByPostIdOrderByOrders(post.getId()).get(0).getPictureUrl();
    }


    public List<SymptomDto> getSymptomList(){
        List<Symptom> symptoms= symptomRepository.findAll();
        return symptoms.stream().map(symptom -> SymptomDto.builder()
                .id(symptom.getId())
                .symptomName(symptom.getSymptomName())
                .build()).toList();

    }

    public List<MaterialDto> getMaterialList(){
        List<Material> materials= materialRepository.findAll();
        return materials.stream().map(material -> MaterialDto.builder()
                .id(material.getId())
                .materialName(material.getMaterialName())
                .functions(material.getFunctions())
                .build()).toList();
    }

    public void createPost(PostWriteDto postWriteDto, CustomUserDetails ent, List<MaterialPost> materialPost){
        Ent findEnt = (Ent)memberRepository.findByEmail(ent.getEmail()).orElseThrow(UserNotFound::new);

        Post postInfo = Post.builder()
                .member(findEnt)
                .title(postWriteDto.getTitle())
                .content(postWriteDto.getContent())
//                .symptom(postWriteDto.getSymptom())
//                .materialList(materialPost)
//                .postImgList(postWriteDto.getPostImgList())
                .build();
        postRepository.save(postInfo);
    }

}