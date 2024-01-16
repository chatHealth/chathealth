package chathealth.chathealth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    @GetMapping
    public String post(Model model, Authentication authentication) {
        boolean isAuth = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuth", isAuth);
        return "post/post";
    }

    @GetMapping("/write")
    public String write() { return "post/write"; }

    // 1. insert
}
