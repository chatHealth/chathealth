package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.PostSearch;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.dto.response.RecentViewPost;
import chathealth.chathealth.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostRestController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> getPosts(PostSearch postSearch) {
        return postService.getPosts(postSearch);
    }

    @GetMapping("/best")
    public List<PostResponse> getBestPostsPerDay() {
        return postService.getBestPostsPerDay();
    }

    @GetMapping("/best-week")
    public List<PostResponse> getBestPostsPerWeek() {
        return postService.getBestPostsPerWeek();
    }

    @GetMapping("/recent-view")
    public List<RecentViewPost> getRecentViewPosts(HttpServletRequest request) {

        return postService.getRecentViewPosts(request);
    }
}