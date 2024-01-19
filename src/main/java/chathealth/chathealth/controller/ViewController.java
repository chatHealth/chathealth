package chathealth.chathealth.controller;


import chathealth.chathealth.dto.request.ReviewDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.dto.response.PostResponse;
import chathealth.chathealth.dto.response.PostResponseDetails;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("view")
public class ViewController {

    private final PostService postService;
    @GetMapping("/{id}")
    public String viewPage(@PathVariable long id, Model model){
        PostResponseDetails post=postService.getAllView(id);
        model.addAttribute("postList",post);
        return "view/view";
    }


    @PostMapping("/reWrite")
    public String insertRe(@RequestBody ReviewDto reviewDto,@AuthenticationPrincipal CustomUserDetails userId){
        reviewDto.setMember(userId.getLoggedMember().getId());
        postService.insertRe(reviewDto);
        return "redirect:/view/1";}




}
