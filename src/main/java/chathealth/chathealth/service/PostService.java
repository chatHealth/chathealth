package chathealth.chathealth.service;


import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.post.PicturePost;
import chathealth.chathealth.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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

}