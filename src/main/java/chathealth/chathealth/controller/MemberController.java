package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.service.MemberService;
import jakarta.validation.Valid;
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

    @ResponseBody
    @GetMapping("/ent/{id}")
    public EntInfoDto getEntInfo(@PathVariable Long id) {
        return memberService.getEntInfo(id);
    }

    @ResponseBody
    @PatchMapping("/user/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody @Valid UserEditDto userEditDto) {
        memberService.updateUserInfo(id, userEditDto);
    }

    @ResponseBody
    @PatchMapping("/ent/{id}")
    public void updateEnt(@PathVariable Long id, @RequestBody @Valid EntEditDto entEditDto) {
        memberService.updateEntInfo(id, entEditDto);
    }
}
