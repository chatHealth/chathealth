package chathealth.chathealth.service;


import chathealth.chathealth.constants.HitPeriod;
import chathealth.chathealth.dto.request.*;
import chathealth.chathealth.dto.response.*;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.message.ReCommnetSelectDto;
import chathealth.chathealth.entity.Helpful;
import chathealth.chathealth.entity.PictureReView;
import chathealth.chathealth.entity.ReComment;
import chathealth.chathealth.entity.Review;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.entity.post.*;
import chathealth.chathealth.exception.BoardNotFoundException;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.*;
import chathealth.chathealth.repository.post.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    private final PostLikeRepository postLikeRepository;
    private final HelpfulRepository helpfulRepository;
    private final ReCommentRepository reCommentRepository;
    private final PostHitRepository postHitRepository;
    private final EntityManager em;



    public List<MaterialSeletDto> getMaterialBySym(long id){
        List<Material> materials=materialRepository.findAllBySymptomId(id);
        return materials.stream().map(material -> MaterialSeletDto.builder()
                .id(material.getId())
                .materialName(material.getMaterialName())
                .build()).collect(Collectors.toList());
    }




    public void deletePost(long id) {
        postRepository.deleteById(id);
    }


    @Transactional
    public void countPostHit(PostHitCountDto postHitCountDto){
        Member member = memberRepository.findById(postHitCountDto.getMember()).orElseThrow();
        Post post = postRepository.findById(postHitCountDto.getPost()).orElseThrow();
        List<PostHit> byMemberAndPost = postHitRepository.findByMemberAndPost(member, post);
      
        PostHit postHit = PostHit.builder()
                .post(post)
                .member(member)
                .createdDate(LocalDateTime.now())
                .build();

        if (byMemberAndPost.isEmpty()) {
            postHitRepository.save(postHit);
        } else {
            List<Optional<PostHit>> CreatePostHit = postHitRepository.findTopByMemberAndPostOrderByCreateDateDesc(member, post);
            LocalDateTime nowTime = LocalDateTime.now();
            PostHit recentHit =CreatePostHit.get(0).orElseThrow();
            long minutes = ChronoUnit.MINUTES.between(recentHit.getCreatedDate(), nowTime);
            if (minutes > 720) {
                postHitRepository.save(postHit);
            }
        }
    }


    @Transactional
    public void modifyPost(PostWriteDto postWriteDto) {
        Post post = postRepository.findById(postWriteDto.getId()).orElseThrow();
        Symptom symptom = symptomRepository.findById(postWriteDto.getSymptom()).orElseThrow();
        post.update(postWriteDto, symptom);
        picturePostRepository.deleteByPost(post);
        for (int i = 0; i < postWriteDto.getPostImgList().size(); i++) {
            if (i == 0) {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(1)
                        .post(post)
                        .build();
                picturePostRepository.save(picturePost);
            } else {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(2)
                        .post(post)
                        .build();
                picturePostRepository.save(picturePost);
            }
        }
        materialPostRepository.deleteAllByPost(post);
        for (int i = 0; i < postWriteDto.getMaterialList().size(); i++) {
            MaterialPost materialPost = MaterialPost.builder()
                    .post(post)
                    .material(materialRepository.findById(postWriteDto.getMaterialList().get(i)).orElseThrow(RuntimeException::new))
                    .build();
            materialPostRepository.save(materialPost);
        }


    }


    public void deleteComment(long num) {
        reCommentRepository.deleteById(num);
    }

    public boolean checkUserRe(long id, CustomUserDetails userid) {
        if (null != userid) {
            if (id == userid.getLoggedMember().getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public List<ReCommnetSelectDto> selectComment(long id, CustomUserDetails userid) {
        Review review = reViewRepository.findById(id).orElseThrow();
        em.clear();
        List<ReComment> rec = reCommentRepository.findAllByReview(review);

        List<ReCommnetSelectDto> redto = rec.stream()
                .filter(ReCommet -> (ReCommet.getMember() instanceof Users || ReCommet.getMember() instanceof Ent))
                .filter(ReCommet -> ReCommet.getDeletedDate() == null)
                .map(ReComment -> {
                    Object member = ReComment.getMember();
                    String profiles = null;
                    String nickName = null;
                    String name = null;
                    String company = null;

                    if (member instanceof Users) {
                        Users user = (Users) member;
                        if (user.getProfile()== null) {
                            profiles = "/img/basic_user.png";
                        }else {
                            profiles = "/profile/" + user.getProfile();
                        }
                        nickName = user.getNickname();
                        name = user.getName();
                    } else if (member instanceof Ent) {
                        Ent ent = (Ent) member;
                        if (ent.getProfile() == null) {
                            profiles = "/img/basic_user.png";
                        }else {
                            profiles = "/profile/" + ent.getProfile();
                        }
                        nickName = null;
                        name = null;
                        company = ent.getCompany();
                    }

                    if (profiles == null) {
                        profiles = "/img/basic_user.png";
                    }

                    return ReCommnetSelectDto.builder()
                            .id(ReComment.getId())
                            .memberId((member instanceof Users) ? ((Users) member).getId() : ((Ent) member).getId())
                            .profile(profiles)
                            .nickName(nickName)
                            .name(name)
                            .company(company)
                            .content(ReComment.getContent())
                            .checkUser(checkUserRe((member instanceof Users) ? ((Users) member).getId() : ((Ent) member).getId(), userid))
                            .createDate(ReComment.getCreatedDate())
                            .build();
                })
                .collect(Collectors.toList());
        return redto;
    }


    public void writeComment(long num, CustomUserDetails id, CommentWriteDto commentWriteDto) {
        ReComment rec = ReComment.builder()
                .review(reViewRepository.findById(num).orElseThrow())
                .member(memberRepository.findById(id.getLoggedMember().getId()).orElseThrow())
                .content(commentWriteDto.getContent())
                .createdDate(LocalDateTime.now())
                .build();
        reCommentRepository.save(rec);
    }


    public Long reviewLikeCheck(long num, CustomUserDetails id) {
        if (id != null) {
            if (helpfulRepository.findByMemberIdAndReviewId(id.getLoggedMember().getId(), num) == null) {
                return Long.valueOf(5);
            } else {
                return Long.valueOf(10);
            }
        } else {
            return Long.valueOf(5);
        }
    }

    @Transactional
    public List<Long> reviewLike(long num, CustomUserDetails userId) {
        List<Long> likeset = new ArrayList<>();
        Long addM;
        if (helpfulRepository.findByMemberIdAndReviewId(userId.getLoggedMember().getId(), num) == null) {
            Helpful helpful = Helpful.builder()
                    .member(memberRepository.findById(userId.getLoggedMember().getId()).orElseThrow())
                    .review(reViewRepository.findById(num).orElseThrow())
                    .build();
            helpfulRepository.save(helpful);
            addM = Long.valueOf(10);
        } else {
            helpfulRepository.deleteByMemberIdAndReviewId(userId.getLoggedMember().getId(), num);
            addM = Long.valueOf(5);
        }
        likeset.add(helpfulRepository.countByReviewId(num));
        likeset.add(addM);
        return likeset;
    }

    public Integer postLikeCheck(long postId, CustomUserDetails id) {
        if (id != null) {
            if (postLikeRepository.findByMemberIdAndPostId(id.getLoggedMember().getId(), postId) == null) {
                return 5;
            } else {
                return 10;
            }
        } else {
            return 5;
        }
    }

    @Transactional
    public List<Long> insertlike(long postId, CustomUserDetails id) {
        List<Long> likeset = new ArrayList<>();
        Long addM;

        if (postLikeRepository.findByMemberIdAndPostId(id.getLoggedMember().getId(), postId) == null) {
            PostLike postLike = PostLike.builder()
                    .member(memberRepository.findById(id.getLoggedMember().getId()).orElseThrow())
                    .post(postRepository.findById(postId).orElseThrow())
                    .build();
            postLikeRepository.save(postLike);
            addM = Long.valueOf(10);
        } else {
            postLikeRepository.deleteByMemberIdAndPostId(id.getLoggedMember().getId(), postId);
            addM = Long.valueOf(5);
        }
        likeset.add(postLikeRepository.countByPostId(postId));
        likeset.add(addM);
        return likeset;
    }


    public void deleteRe(long num) {
        Review re = reViewRepository.findById(num).orElseThrow();
        reViewRepository.delete(re);
    }

    @Transactional
    public void modifyReView(long num, ReviewModDto reviewModDto) {
        Review re = reViewRepository.findById(num).orElseThrow(BoardNotFoundException::new);
        re.update(reviewModDto);

        if (reviewModDto.getPictureReList().size() > 0) {
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


    public Page<ReViewSelectDto> getReview(long id, CustomUserDetails login, Pageable pageable) {
        Post post = postRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        ReViewSelectDto userCheck = new ReViewSelectDto();

        Page<Review> reviewPage = reViewRepository.findAllByPost(post, pageable);

        List<ReViewSelectDto> dtoList = reviewPage.getContent().stream()
                .filter(review -> (review.getMember() instanceof Users))
                .filter(review -> review.getDeletedDate() == null)
                .map(review -> {
                    Users user = (Users) review.getMember();
                    String profiles = "/profile/" + user.getProfile();
                    if (user.getProfile()==null) {
                        profiles = "/img/basic_user.png";
                    }
                    return ReViewSelectDto.builder()
                            .id(review.getId())
                            .member(user.getId())
                            .content(review.getContent())
                            .score(review.getScore())
                            .nickName(user.getNickname())
                            .profile(profiles)
                            .name(user.getName())
                            .helpful(helpfulRepository.countByReviewId(review.getId()))
                            .helpfulCheck(reviewLikeCheck(review.getId(), login))
                            .same(userCheck.sameclass(user.getId(), login))
                            .reCommentCount(reCommentRepository.countByReviewAndDeletedDateIsNull(review))
                            .pictureReView(review.getPictureReList().stream().map(PictureReView::getPictureUrl).toList())
                            .createdDate(review.getCreatedDate())
                            .build();
                }).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, reviewPage.getTotalElements());
    }


    public Review insertRe(ReviewDto reviewDto) {
        Review re = Review.builder()
                .post(postRepository.findById(reviewDto.getPost()).orElseThrow())
                .member(memberRepository.findById(reviewDto.getMember()).orElseThrow())
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .build();
        reViewRepository.save(re);
        for (int i = 0; i < reviewDto.getPictureReList().size(); i++) {
            PictureReView pic = PictureReView.builder()
                    .review(re)
                    .pictureUrl(reviewDto.getPictureReList().get(i))
                    .build();
            pictureReviewRepository.save(pic);
        }
        return re;
    }


    public PostResponseDetails getAllView(long id) {
        Post post = postRepository.findById(id).orElseThrow();
        double nnumm=0.0;
        if(reViewRepository.countByPostIdAndDeletedDateIsNull(id)>0) {
            double score = reViewRepository.findAverageScoreByPostIdAndDeletedDateIsNull(id);
            double num = (Math.round(score * 10));
            nnumm = num / 10;
        }
        return PostResponseDetails.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .company(post.getMember().getCompany())
                .title(post.getTitle())
                .likeCount(postLikeRepository.countByPostId(post.getId()))
                .content(post.getContent())
                .score(nnumm)
                .reviewCount(reViewRepository.countByPostIdAndDeletedDateIsNull(id))
                .picturePost(picturePostRepository.findAllByPostId(id).stream().map(PicturePost::getPictureUrl).toList())
                .material(PostResponseDetails.extractMaterialNames(post.getMaterialList()))
                .materialInfo(PostResponseDetails.extractMaterialNames(post.getMaterialList()))
                .build();
    }

    public PostModResponseDto getAllViewMod(long id) {
        Post post = postRepository.findById(id).orElseThrow();

        List<Material> materialId = materialPostRepository.findAllByPost(post).stream().map(MaterialPost::getMaterial).toList();
        List<Long> materialLong = materialId.stream().map(Material::getId).toList();

        return PostModResponseDto.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .symptom(post.getSymptom().getId())
                .picturePostMain(picturePostRepository.findByPostIdAndOrders(id, 1).getPictureUrl())
                .picturePostSer(picturePostRepository.findAllByPostIdAndOrders(id, 2).stream().map(PicturePost::getPictureUrl).toList())
                .materialId(materialLong)
                .build();
    }

    public List<MaterialDto> getMaterialByPost(long id){
        Post post=postRepository.findById(id).orElseThrow();
        List<MaterialPost> materialPosts=materialPostRepository.findAllByPost(post);
        return materialPosts.stream().map(materialPost -> MaterialDto.builder()
                .materialName(materialPost.getMaterial().getMaterialName())
                .functions(materialPost.getMaterial().getFunctions())
                .build()).collect(Collectors.toList());
    }

    public List<RelatedProductDto> relatedProduct(long id){
        Post post=postRepository.findById(id).orElseThrow();
        List<MaterialPost> materialIdList=materialPostRepository.findAllByPost(post); //포스트값의 선택된 메테리얼값 리스트
        List<Material> materialId=new ArrayList<>();
        for(int i=0;i<materialIdList.size();i++){
            materialId.add(materialRepository.findById(materialIdList.get(i).getMaterial().getId()).orElseThrow());
        }
        List<Post> findPost=postRepository.findByMaterialListMaterialIdIn(materialId.stream().map(Material::getId).toList()); //메테리얼값리스트의 값들을 가지는 post들 찾기
        List<RelatedProductDto> dto= findPost.stream()
                .filter(find -> !find.getId().equals(id))
                .map(find->RelatedProductDto.builder()
                .postId(find.getId())
                .title(find.getTitle())
                .postLike(find.getPostLikeCount())
                .postImg(find.getPostImgList().get(0).getPictureUrl())
                .build()).toList();
        dto.stream().skip(4).collect(Collectors.toList());
        return dto;
    }

    // 포스트 목록 조회
    public List<PostResponse> getPosts(PostSearch postSearch) {

        Long count = postRepository.getPostsCount(postSearch);

        // 페이지가 존재하지 않을 경우 마지막 페이지로 이동
        if (count / postSearch.getSize() < postSearch.getPage()) {
            postSearch.setPage((int) (count / postSearch.getSize()));
        }
        return postRepository.getPosts(postSearch).stream()
                .map(post -> {
                    String representativeImg = null;
                    if (post.getPostImgList() != null && !post.getPostImgList().isEmpty()) {
                        representativeImg = post.getPostImgList().stream()
                                .filter(img -> img.getOrders() == 1)
                                .findFirst()
                                .map(PicturePost::getPictureUrl)
                                .orElse(null);
                    }
                    int reviews = post.getReviewList().stream()
                            .filter(review -> review.getDeletedDate() == null)
                            .toList().size();

                    return PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .company(post.getMember().getCompany())
                            .representativeImg(representativeImg)
                            .count(count)
                            .createdAt(post.getCreatedDate())
                            .hitCount(post.getPostHitCount())
                            .likeCount(post.getPostLikeCount())
                            .reviewCount(reviews)
                            .build();
                })
                .toList();
    }

    public List<PostResponse> getBestPostsPerDay() {
        return postRepository.getBestPostsPerDay().stream()
                .map(post -> createRecentPostResponse(post, HitPeriod.DAY))
                .toList();
    }

    public List<PostResponse> getBestPostsPerWeek() {
        return postRepository.getBestPostsPerWeek().stream()
                .map(post -> createRecentPostResponse(post, HitPeriod.WEEK))
                .toList();
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

    public Long createPost(PostWriteDto postWriteDto, CustomUserDetails ent) {
        Ent findEnt = (Ent) memberRepository.findByEmail(ent.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);

        Post postInfo = Post.builder()
                .member(findEnt)
                .title(postWriteDto.getTitle())
                .content(postWriteDto.getContent())
                .symptom(symptomRepository.findById(postWriteDto.getSymptom()).orElseThrow())
                .build();
        Post savedpost = postRepository.save(postInfo);
        for (int i = 0; i < postWriteDto.getMaterialList().size(); i++) {
            MaterialPost materialPost = MaterialPost.builder()
                    .post(savedpost)
                    .material(materialRepository.findById(postWriteDto.getMaterialList().get(i)).orElseThrow(RuntimeException::new))
                    .build();
            materialPostRepository.save(materialPost);
        }
        for (int i = 0; i < postWriteDto.getPostImgList().size(); i++) {
            if (i == 0) {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(1)
                        .post(savedpost)
                        .build();
                picturePostRepository.save(picturePost);
            } else {
                PicturePost picturePost = PicturePost.builder()
                        .pictureUrl(postWriteDto.getPostImgList().get(i))
                        .orders(2)
                        .post(savedpost)
                        .build();
                picturePostRepository.save(picturePost);
            }
        }
       return savedpost.getId();
    }

    public List<MaterialSymptomDto> getMaterialBySymptomType(){
        List<Symptom> symptoms=symptomRepository.findAllFetch();

        List<MaterialSymptomDto> materials=new ArrayList<>();

        for(Symptom symptom:symptoms){
            List<String> materialName=symptom.getMaterialList().stream().map(Material::getMaterialName).collect(Collectors.toList());
            MaterialSymptomDto materialSymptomDto=new MaterialSymptomDto(symptom.getSymptomName(),materialName);
            materials.add(materialSymptomDto);
        }

        return materials.stream()
                .sorted(Comparator.comparingInt(o -> SymptomType.valueOf(String.valueOf(o.getSymptomName())).ordinal()))
                .toList();

    }

    private PostResponse createRecentPostResponse(Post post, HitPeriod period) {
        String representativeImg = null;
        if (post.getPostImgList() != null && !post.getPostImgList().isEmpty()) {
            representativeImg = post.getPostImgList().stream()
                    .filter(img -> img.getOrders() == 1)
                    .findFirst()
                    .map(PicturePost::getPictureUrl)
                    .orElse(null);
        }

        int size = 0;

        if(period == HitPeriod.DAY){
            size = post.getPostHitList().stream().filter(postHit -> postHit.getCreatedDate().isAfter(LocalDateTime.now().minusDays(1))).toList().size();
        }else if(period == HitPeriod.WEEK){
            size = post.getPostHitList().stream().filter(postHit -> postHit.getCreatedDate().isAfter(LocalDateTime.now().minusWeeks(1))).toList().size();
        }
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .hitCount(size)
                .representativeImg(representativeImg)
                .build();
    }

    public void postRecentView(Long postId, HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String recentViewCookie = "recentView";
        LinkedList<String> recentViews = new LinkedList<>();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(recentViewCookie)) {
                    recentViews = new LinkedList<>(Arrays.asList(cookie.getValue().split("\\^")));
                    recentViews.removeIf(recentView -> recentView.equals(String.valueOf(postId)));
                    break;
                }
            }
        }

        recentViews.addFirst(postId.toString());

        if(recentViews.size() > 5) {
            recentViews.removeLast();
        }

        String value = String.join("^", recentViews);

        Cookie cookie = new Cookie(recentViewCookie, value);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        cookie.setHttpOnly(false);

        response.addCookie(cookie);
    }

    public List<RecentViewPost> getRecentViewPosts(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String recentViewCookie = "recentView";
        ArrayList<String> recentViews = new ArrayList<>();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(recentViewCookie)) {
                    recentViews = new ArrayList<>(Arrays.asList(cookie.getValue().split("\\^")));
                    break;
                }
            }
        }

        if (recentViews.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> recentPosts = postRepository.findAllByIdIn(recentViews.stream().map(
                id -> {
                    try {
                        return Long.parseLong(id);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }).toList());
        ArrayList<String> finalRecentViews = recentViews;
        recentPosts.sort(Comparator.comparing(post -> finalRecentViews.indexOf(post.getId().toString())));


        return recentPosts.stream().map(
                post ->
                {
                    String representativeImg = post.getPostImgList().stream()
                            .filter(img -> img.getOrders() == 1)
                            .findFirst()
                            .map(PicturePost::getPictureUrl)
                            .orElse(null);

                    return RecentViewPost.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .representativeImg("/profile/" + representativeImg)
                            .build();
                }
        ).toList();
    }
}