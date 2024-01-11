package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @ResponseBody
    //@PreAuthorize("hasRole('USER')") //USER 롤 가지고 있는 사람만 메서드 실행 가능
    @GetMapping("/user/{id}")
    public UserInfoDto getUserInfo(@PathVariable Long id, Model model) {
//        model.addAttribute("userInfo", memberService.getUserInfo(id));
//        return "member/user-info";
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
