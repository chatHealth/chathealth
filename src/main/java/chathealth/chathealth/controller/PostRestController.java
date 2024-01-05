package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/post")
    public List<PostResponse> getPosts(PostSearch postSearch) {
        return postService.getPosts(postSearch);
    }
}
