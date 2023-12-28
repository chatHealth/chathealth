package chathealth.chathealth.controller;

import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @ResponseBody
    @GetMapping("/user/{id}")
    public UserInfoDto getUserInfo(@PathVariable Long id) {
        return memberService.getUserInfo(id);
    }
}
