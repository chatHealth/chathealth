package chathealth.chathealth.controller;


import chathealth.chathealth.dto.request.PostWriteDto;
import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.dto.response.MaterialDto;
import chathealth.chathealth.dto.response.SymptomDto;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.post.Material;
import chathealth.chathealth.entity.post.MaterialPost;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/write")
    public String write(Model model) {
        List<SymptomDto> symptom=postService.getSymptomList();
        model.addAttribute("symptom",symptom);
        List<MaterialDto> material=postService.getMaterialList();
        model.addAttribute("material",material);
        return "post/write";
    }

    // 1. insert
    @PostMapping("/write")
    public void postWriteProcess(@ModelAttribute PostWriteDto postWriteDto,
                                 @AuthenticationPrincipal CustomUserDetails ent,
                                 @RequestParam("symptomOptions") Long symptomId,
                                 @RequestParam("materialOptions") List<Long> selectMaterial){
        log.info("postWriteDto==={}", postWriteDto);
        postService.createPost(postWriteDto, ent,symptomId, selectMaterial);

    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(@RequestParam MultipartFile upload){
        String originalFile = upload.getOriginalFilename(); // 이게 진짜 파일 이름...
        String renamedFile = null;
        String folder =  null;
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        folder = simpleDateFormat.format(now);
        File dir = new File(uploadFolder+File.separator+folder);
        if(!dir.exists()) dir.mkdirs();

        // file이름 분리하고 확장자 분리
        String fileName = originalFile.substring(0,originalFile.lastIndexOf("."));
        String ext = originalFile.substring(originalFile.lastIndexOf("."));
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strNow = simpleDateFormat.format(now);
        log.info("strNow==={}",strNow);
        renamedFile = fileName+"_"+strNow+ext;
        Path imgFilePath = Paths.get(dir+File.separator+renamedFile);

        try {
            Files.write(imgFilePath,upload.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap =  new HashMap<>();
        resultMap.put("uploaded",true);
        resultMap.put("url","/upload/"+folder+"/"+renamedFile);
        return resultMap;


    }
}
