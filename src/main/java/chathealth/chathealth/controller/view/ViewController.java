package chathealth.chathealth.controller.view;


import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.PostResponseDetails;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("view")
public class ViewController {

    private final PostService postService;
    @GetMapping("/{id}")
    public String viewPage(@PathVariable long id, Model model,@AuthenticationPrincipal CustomUserDetails userid){
        //post정보
        PostResponseDetails post=postService.getAllView(id);
        if(userid==null){
            model.addAttribute("userCheck",0);
        }else {
            model.addAttribute("userCheck",1);
        }
        model.addAttribute("postUserLike",postService.postLikeCheck(id,userid));
        model.addAttribute("postList",post);
        return "view/view";
    }
    @PostMapping("/like/{postId}")
    @ResponseBody
    public List<Long> likeinsert(@PathVariable long postId,@AuthenticationPrincipal CustomUserDetails id){
        return postService.insertlike(postId,id);
    }
}
