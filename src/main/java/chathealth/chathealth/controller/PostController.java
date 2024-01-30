package chathealth.chathealth.controller;


import chathealth.chathealth.dto.request.PostWriteDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.MaterialDto;
import chathealth.chathealth.dto.response.SymptomDto;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    @Value("${file.path}")
    private String uploadFolder;

    @GetMapping
    public String post(Model model, Authentication authentication) {
        boolean isAuth = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuth", isAuth);
        return "post/post";
    }

    @Secured({"ROLE_ADMIN","ROLE_PERMITTED_ENT"})
    @GetMapping("/write")
    public String write(Model model) {
        List<SymptomDto> symptom=postService.getSymptomList();
        List<MaterialDto> material=postService.getMaterialList();
        model.addAttribute("symptom",symptom);
        model.addAttribute("material",material);
        return "post/write";
    }


    @Secured({"ROLE_ADMIN","ROLE_PERMITTED_ENT"})
    @PostMapping("/write")
    @ResponseBody
    public void postWriteProcess(@RequestBody PostWriteDto postWriteDto,@AuthenticationPrincipal CustomUserDetails ent){
        postService.createPost(postWriteDto,ent);
    }


    @GetMapping("/modify")
    public String postModifyProcess(@RequestParam long postId,Model model){
        List<SymptomDto> symptom=postService.getSymptomList();
        List<MaterialDto> material=postService.getMaterialList();
        model.addAttribute("symptom",symptom);
        model.addAttribute("material",material);
        model.addAttribute("postList",postService.getAllViewMod(postId));
        log.info("postListtttt======{}",postService.getAllViewMod(postId));
        return "post/modify";
    }


    @PostMapping("/modWrite")
    @ResponseBody
    public String postModifyWrite(@RequestBody PostWriteDto postWriteDto){
        postService.modifyPost(postWriteDto);
        log.info("postWWWWW======={}",postWriteDto);
        return "post";
    }

    @DeleteMapping("/delete/{postId}")
    @ResponseBody
    public String deletePost(@PathVariable long postId){
        postService.deletePost(postId);
        return "post";
    }
}
