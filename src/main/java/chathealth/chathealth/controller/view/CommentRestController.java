package chathealth.chathealth.controller.view;


import chathealth.chathealth.dto.request.CommentWriteDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.message.ReCommnetSelectDto;
import chathealth.chathealth.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentRestController {

    private final PostService postService;

    @GetMapping("/{id}")
    public List<ReCommnetSelectDto> selectReCommnet(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails userid){
        return postService.selectComment(id,userid);
    }

    @PostMapping("/write/{num}")
    public void writeComment(@PathVariable long num, @AuthenticationPrincipal CustomUserDetails id,@RequestBody CommentWriteDto commentWriteDto){
     postService.writeComment(num,id,commentWriteDto);
    }

    @DeleteMapping("/delete/{num}")
    public void deleteComment(@PathVariable long num){
        postService.deleteComment(num);
    }

}
