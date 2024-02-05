package chathealth.chathealth.controller.view;


import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.request.ReviewDto;
import chathealth.chathealth.dto.request.ReviewModDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.dto.response.ReViewSelectDto;
import chathealth.chathealth.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ViewRestController {
    private final PostService postService;


    @GetMapping("/{postId}")
    public Page<ReViewSelectDto> selectRe(@PathVariable long postId, @AuthenticationPrincipal CustomUserDetails login, Pageable pageable){
        Page<ReViewSelectDto> select=postService.getReview(postId,login,pageable);
        return select;
    }



    @Secured("ROLE_USER")
    @PostMapping("/write")
    public void insertRe(@Valid @RequestBody ReviewDto reviewDto, @AuthenticationPrincipal CustomUserDetails userId){
        reviewDto.setMember(userId.getLoggedMember().getId());
        postService.insertRe(reviewDto);}
    @PostMapping("/mod/{num}")
    public void modRe(@PathVariable long num, @RequestBody ReviewModDto reviewModDto){
        postService.modifyReView(num,reviewModDto);
    }

    @DeleteMapping("/delete/{num}")
    public void deleteRe(@PathVariable long num){
        postService.deleteRe(num);
    }


    @PostMapping("/like/{num}")
    public List<Long> reviewlike(@PathVariable long num,@AuthenticationPrincipal CustomUserDetails userId){
        return postService.reviewLike(num,userId);
    }
}


