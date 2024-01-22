package chathealth.chathealth.controller;


import chathealth.chathealth.dto.response.ReViewSelectDto;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ViewRestController {
    private final PostService postService;


    @GetMapping("/{postId}")
    @ResponseBody
    public List<ReViewSelectDto> selectRe(@PathVariable long postId){
        log.info("ttttsle===={}",postId);
        List<ReViewSelectDto> select= postService.getReview(postId);
        log.info("seltttt===={}",select);
        return select;
    }
}
