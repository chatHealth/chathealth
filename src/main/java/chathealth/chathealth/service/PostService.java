package chathealth.chathealth.service;


import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.entity.post.Post;
import chathealth.chathealth.repository.PicturePostRepository;
import chathealth.chathealth.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PicturePostRepository picturePostRepository;

    // public Post insertPost(){}

    // 포스트 목록 조회
    public List<PostResponse> getPosts(PostSearch postSearch) {

        return postRepository.getPosts(postSearch).stream()
                .map(post ->
                        PostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .createdDate(post.getCreatedDate())
                                .company(post.getMember().getCompany())
                                .representativeImg(getRepresentativeImg(post))
                                .build())
                .toList();
    }

    private String getRepresentativeImg(Post post) {
        return picturePostRepository.findAllByPostIdOrderByOrders(post.getId()).get(0).getPictureUrl();
    }

}