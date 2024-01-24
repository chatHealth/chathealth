package chathealth.chathealth.service;


import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.request.PostWriteDto;
import chathealth.chathealth.dto.request.ReviewDto;
import chathealth.chathealth.dto.request.ReviewModDto;
import chathealth.chathealth.dto.response.*;
import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.entity.post.Symptom;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.*;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.repository.PicturePostRepository;
import chathealth.chathealth.repository.post.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PicturePostRepository picturePostRepository;
    private final SymptomRepository symptomRepository;
    private final MaterialRepository materialRepository;
    private final MemberRepository memberRepository;
    private final MaterialPostRepository materialPostRepository;
    private final PictureReviewRepository pictureReviewRepository;
    private final ReViewRepository reViewRepository;


    public void deleteRe(long num){
        Review re= reViewRepository.findById(num).orElseThrow();
        reViewRepository.delete(re);
    }

@Transactional
    public void modifyReView(long num, ReviewModDto reviewModDto){
    Review re = reViewRepository.findById(num).orElseThrow(BoardNotFoundException::new);
    re.update(reviewModDto);

        if(reviewModDto.getPictureReList().size()>0) {
            pictureReviewRepository.deleteAllByReview(re);
            for (int i = 0; i < reviewModDto.getPictureReList().size(); i++) {
                PictureReView pic = PictureReView.builder()
                        .review(re)
                        .pictureUrl(reviewModDto.getPictureReList().get(i))
                        .build();
                pictureReviewRepository.save(pic);
            }
        }
    }


    public List<ReViewSelectDto> getReview(long id,CustomUserDetails login){
        Post post = postRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        ReViewSelectDto userCheck = new ReViewSelectDto();
        List<Review> re=reViewRepository.findAllByPost(post);
        List<ReViewSelectDto> dto=re.stream()
                .filter(review -> (review.getMember() instanceof Users))
                .filter(review-> review.getDeletedDate()==null)
                .map(Review -> {
//                    if(Review.getDeletedDate()==null){
                    Users user = (Users) Review.getMember();
                    String profiles="/profile/"+ user.getProfile();
                    if(user.getProfile().endsWith("_")){
                        profiles="/img/basic_user.png";
                    }
            return ReViewSelectDto.builder()
                    .id(Review.getId())
                    .member(user.getId())
                    .content(Review.getContent())
                    .score(Review.getScore())
                    .nickName(user.getNickname())
                    .profile(profiles)
                    .same(userCheck.sameclass(user.getId(),login))
                    .pictureReView(Review.getPictureReList().stream().map(PictureReView::getPictureUrl).toList())
                    .createdDate(Review.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();
        }).collect(Collectors.toList());


        return dto;
    }
    public Review insertRe(ReviewDto reviewDto){
        Review re= Review.builder()
                .post(postRepository.findById(reviewDto.getPost()).orElseThrow())
                .member(memberRepository.findById(reviewDto.getMember()).orElseThrow())
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .build();
        reViewRepository.save(re);
        for(int i=0;i<reviewDto.getPictureReList().size();i++){
            PictureReView pic=PictureReView.builder()
                    .review(re)
                    .pictureUrl(reviewDto.getPictureReList().get(i))
                    .build();
            pictureReviewRepository.save(pic);
        }
        return re;
    }


    public PostResponseDetails getAllView(long id){
        Post post=postRepository.findById(id).orElseThrow();
        return PostResponseDetails.builder()
                .id(post.getId())
                .company(post.getMember().getCompany())
                .title(post.getTitle())
                .content(post.getContent())
                .picturePost(picturePostRepository.findAllByPostId(id).stream().map(PicturePost::getPictureUrl).toList())
                .material(PostResponseDetails.extractMaterialNames(post.getMaterialList()))
                .build();
    }

    // 포스트 목록 조회
    public List<PostResponse> getPosts(PostSearch postSearch) {

        Long count = postRepository.getPostsCount(postSearch);

        // 페이지가 존재하지 않을 경우 마지막 페이지로 이동
        if (count / postSearch.getSize() < postSearch.getPage()) {
            postSearch.setPage((int) (count / postSearch.getSize()));
        }
        return postRepository.getPosts(postSearch).stream()
                .map(post ->{
                    String representativeImg = null;
                    if(post.getPostImgList() != null && !post.getPostImgList().isEmpty()){
                        representativeImg = post.getPostImgList().stream()
                                .filter(img -> img.getOrders() == 0)
                                .findFirst()
                                .map(PicturePost::getPictureUrl)
                                .orElse(null);
                    }

                        return PostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .company(post.getMember().getCompany())
                                .representativeImg(representativeImg)
                                .count(count)
                                .createdAt(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .hitCount(post.getPostHitCount())
                                .likeCount(post.getPostLikeCount())
                                .reviewCount(post.getReviewCount())
                                .symptom(post.getSymptom().getSymptomName())
                                .build();})
                .toList();
    }

    public List<PostResponse> getBestPostsPerDay() {
        return postRepository.getBestPostsPerDay().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .hitCount(post.getPostHitCount())
                        .build())
                .toList();
    }

    public List<PostResponse> getBestPostsPerWeek() {
        return postRepository.getBestPostsPerWeek().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .hitCount(post.getPostHitCount())
                        .build())
                .toList();
    }

    public List<PostResponse> getRecentPosts(Member member) {
        return postRepository.getRecentPosts(member).stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .toList();
    }

    private String getRepresentativeImg(Post post) {
        List<PicturePost> pictures = picturePostRepository.findAllByPostIdOrderByOrders(post.getId());
        if (pictures.isEmpty()) {
            return null;
        }
        return pictures.get(0).getPictureUrl();
    }


    public List<SymptomDto> getSymptomList() {
        List<Symptom> symptoms = symptomRepository.findAll();
        return symptoms.stream().map(symptom -> SymptomDto.builder()
                .id(symptom.getId())
                .symptomName(symptom.getSymptomName())
                .build()).toList();

    }

    public List<MaterialDto> getMaterialList() {
        List<Material> materials = materialRepository.findAll();
        return materials.stream().map(material -> MaterialDto.builder()
                .id(material.getId())
                .materialName(material.getMaterialName())
                .functions(material.getFunctions())
                .build()).toList();
    }

    public void createPost(PostWriteDto postWriteDto, CustomUserDetails ent) {
        Ent findEnt = (Ent) memberRepository.findByEmail(ent.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);

        Post postInfo = Post.builder()
                .member(findEnt)
                .title(postWriteDto.getTitle())
                .content(postWriteDto.getContent())
                .symptom(symptomRepository.findById(postWriteDto.getSymptom()).orElseThrow())
                .build();
        Post savedpost = postRepository.save(postInfo);
        for (int i=0;i<postWriteDto.getMaterialList().size();i++) {
            MaterialPost materialPost = MaterialPost.builder()
                    .post(savedpost)
                    .material(materialRepository.findById(postWriteDto.getMaterialList().get(i)).orElseThrow(RuntimeException::new))
                    .build();
            materialPostRepository.save(materialPost);
        }
        for(int i=0;i<postWriteDto.getPostImgList().size();i++) {
            if(i==0) {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(1)
                        .post(savedpost)
                        .build();
                picturePostRepository.save(picturePost);
            }else {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(2)
                        .post(savedpost)
                        .build();
                picturePostRepository.save(picturePost);
            }
        }
}
}