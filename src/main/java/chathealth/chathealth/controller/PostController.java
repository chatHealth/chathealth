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
        model.addAttribute("symptom",symptom);
        List<MaterialDto> material=postService.getMaterialList();
        model.addAttribute("material",material);
        return "post/write";
    }

    // 1. insert
//    @PostMapping("/write")
//    public void postWriteProcess(@RequestBody PostWriteDto postWriteDto,
//                                 @AuthenticationPrincipal CustomUserDetails ent,
//                                 @RequestParam("symptom") Long symptomId,
//                                 @RequestParam("material") List<Long> selectMaterial,
//                                 @RequestParam List<String> postImg){
//        log.info("postWriteDto==={}", postWriteDto);
//
//        postService.createPost(postWriteDto, ent,symptomId, selectMaterial,postImg);
//
//    }

    @Secured({"ROLE_ADMIN","ROLE_PERMITTED_ENT"})
    @PostMapping("/write")
    @ResponseBody
    public void postWriteProcess(@RequestBody PostWriteDto postWriteDto,@AuthenticationPrincipal CustomUserDetails ent){
        postService.createPost(postWriteDto,ent);
    }





}
