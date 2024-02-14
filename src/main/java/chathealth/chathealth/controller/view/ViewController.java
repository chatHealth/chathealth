package chathealth.chathealth.controller.view;


import chathealth.chathealth.dto.request.PostHitCountDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.PostResponseDetails;
import chathealth.chathealth.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String viewPage(@PathVariable long id, Model model, @AuthenticationPrincipal CustomUserDetails userid,
                           HttpServletRequest request, HttpServletResponse response){

        //최근 본 게시물
        postService.postRecentView(id,request,response);
        //post정보
        PostResponseDetails post=postService.getAllView(id);
        if(userid==null){
            model.addAttribute("userCheck",0);
        }else {
            PostHitCountDto postHitCountDto=PostHitCountDto.builder()
                    .post(id)
                    .member(userid.getLoggedMember().getId())
                    .build();
            postService.countPostHit(postHitCountDto);
            model.addAttribute("userCheck",1);
            model.addAttribute("userId",userid.getLoggedMember().getId());
        }
        model.addAttribute("material",postService.getMaterialByPost(id));
        model.addAttribute("postUserLike",postService.postLikeCheck(id,userid));
        model.addAttribute("postList",post);
        model.addAttribute("related",postService.relatedProduct(id));
        return "view/view";
    }
    @PostMapping("/like/{postId}")
    @ResponseBody
    public List<Long> likeinsert(@PathVariable long postId,@AuthenticationPrincipal CustomUserDetails id){
        return postService.insertlike(postId,id);
    }
}
