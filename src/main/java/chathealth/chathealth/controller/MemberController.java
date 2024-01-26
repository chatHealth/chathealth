package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.PostLikeDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.service.AuthService;
import chathealth.chathealth.service.MailService;
import chathealth.chathealth.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;
    private final MailService mailService;

    @PreAuthorize("hasRole('ROLE_USER')") //USER 롤 가지고 있는 사람만 메서드 실행 가능
    @GetMapping("/user/{id}")  //개인 마이페이지로 이동
    public String goUserInfo(@PathVariable Long id, Model model) {
        UserInfoDto userInfoDto = memberService.getUserInfo(id);
        model.addAttribute("userInfo", userInfoDto);
        return "member/user-info";

    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT')")
    @GetMapping("/ent/{id}")  //사업자 마이페이지로 이동
    public String goEntInfo(@PathVariable Long id,Model model) {
        EntInfoDto entInfoDto = memberService.getEntInfo(id);
        model.addAttribute("entInfo", entInfoDto);
        log.info(String.valueOf(entInfoDto));
        return "member/ent-info";
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_USER')")
    @Transactional
    @ResponseBody
    @PatchMapping("/user/{id}") //개인도 사업자도 프로필은 여기서 바꾼다
    public Map<String,String> updateUserProfile(@PathVariable Long id, MultipartFile changeProfile) {
        memberService.updateProfile(id, changeProfile);
        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("isUpdated","isUpdated");
        return resultMap;
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @ResponseBody
    @PatchMapping("/user/updateInfo/{id}")
    public Map<String,String> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UserEditDto userEditDto) {
        Address addressEntity = Address.builder()
                .postcode(userEditDto.getPostcode())
                .address(userEditDto.getFrontAddress())
                .addressDetail(userEditDto.getAddressDetail())
                .build();
        userEditDto.setAddress(addressEntity);
        memberService.updateUserInfo(id,userEditDto);
        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("isUpdated","isUpdated");
        return resultMap;
        }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT')")
    @Transactional
    @ResponseBody
    @PatchMapping("/ent/updateInfo/{id}")
    public Map<String,String> updateEnt(@PathVariable Long id, @RequestBody @Valid EntEditDto entEditDto) {
        Address addressEntity = Address.builder()
                .postcode(entEditDto.getPostcode())
                .address(entEditDto.getFrontAddress())
                .addressDetail(entEditDto.getAddressDetail())
                .build();
        entEditDto.setAddress(addressEntity);
        memberService.updateEntInfo(id,entEditDto);
        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("isUpdated","isUpdated");
        return resultMap;
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_USER')")
    @ResponseBody
    @PostMapping("/emails/verification-request")
    public String sendMail(String email) throws MessagingException{
        return mailService.sendEmail(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT','ROLE_USER')")
    @PatchMapping("/update-pw/{id}")
    @ResponseBody
    public Map<String,String> updateUserPw(@PathVariable Long id, String pw){
        authService.updatePw(id,pw);
        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("isUpdated","isUpdated");
        return resultMap;
    }

    @GetMapping("/post-like/{id}")
    @ResponseBody
    public List<PostLikeDto> getPostLike(@PathVariable Long id){
        return memberService.getPostLike(id);}


    @GetMapping("/user/getinfo/{id}")
    @ResponseBody
    public UserInfoDto getUserInfo(@PathVariable String id){
        Long getId = Long.parseLong(id);
        return memberService.getUserInfo(getId);
    }

    @GetMapping("/ent/getinfo/{id}")
    @ResponseBody
    public EntInfoDto getEntInfo(@PathVariable String id){
        Long getId = Long.parseLong(id);
        return memberService.getEntInfo(getId);
    }
}
