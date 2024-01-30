package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/api/post")
    public List<PostResponse> getPosts(PostSearch postSearch) {
        return postService.getPosts(postSearch);
    }

    @GetMapping("/api/post/best")
    public List<PostResponse> getBestPostsPerDay() {
        return postService.getBestPostsPerDay();
    }

    @GetMapping("/api/post/best-week")
    public List<PostResponse> getBestPostsPerWeek() {
        return postService.getBestPostsPerWeek();
    }

    @GetMapping("/api/post/recent")
    public List<PostResponse> getRecentPosts(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return postService.getRecentPosts(customUserDetails.getLoggedMember());
    }
}