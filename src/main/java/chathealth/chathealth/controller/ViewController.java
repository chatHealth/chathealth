package chathealth.chathealth.controller;


import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.dto.response.PostResponseDetails;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("view")
public class ViewController {

    private final PostService postService;
    @GetMapping("/{id}")
    public String viewPage(@PathVariable long id, Model model){
        PostResponseDetails post=postService.getAllView(id);
        log.info("postpostpost======={}",post);
        model.addAttribute("postList",post);
        return "view/view";
    }




}
