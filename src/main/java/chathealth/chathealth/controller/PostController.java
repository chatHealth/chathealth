package chathealth.chathealth.controller;


import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String post() { return "post/post"; }

    @GetMapping("/write")
    public String write() { return "post/write"; }

    // 1. insert
}
