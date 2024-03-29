package chathealth.chathealth.controller;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.dto.request.member.EntEditDto;
import chathealth.chathealth.dto.request.member.UserEditDto;
import chathealth.chathealth.dto.response.member.*;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.service.AuthService;
import chathealth.chathealth.service.MailService;
import chathealth.chathealth.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String goUserInfo(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user , Model model) {
        if(!user.getLoggedMember().getId().equals(id)) {
            throw new NotPermitted("권한이 없습니다");
        }

        UserInfoDto userInfoDto = memberService.getUserInfo(id);
        model.addAttribute("userInfo", userInfoDto);
        return "member/user-info";
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_REJECTED_ENT')")
    @GetMapping("/ent/{id}")  //사업자 마이페이지로 이동
    public String goEntInfo(@PathVariable Long id,Model model) {
        EntInfoDto entInfoDto = memberService.getEntInfo(id);
        model.addAttribute("entInfo", entInfoDto);
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
    @PatchMapping("/user/updateInfo/{id}") //개인 인포 업데이트
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
    @PatchMapping("/ent/updateInfo/{id}")  //사업자 인포 업데이트
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
    @PostMapping("/emails/verification-request")  //이메일 인증
    public String sendMail(String email) throws MessagingException{
        return mailService.sendVerification(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_WAITING_ENT','ROLE_PERMITTED_ENT','ROLE_USER')")
    @PatchMapping("/update-pw/{id}")
    @ResponseBody
    public Map<String,String> updateUserPw(@PathVariable Long id, String pw, String email){  //비밀번호 변경
        authService.updatePw(id,pw);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("isUpdated","isUpdated");
        try {
            mailService.sendNoticePwChanged(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    @GetMapping("/post-like/{id}")
    @ResponseBody
    public List<PostLikeDto> getPostLike(@PathVariable Long id){  //관심상품 가져오기
        return memberService.getPostLike(id);}

    @GetMapping("/myReview/{id}")
    @ResponseBody
    public List<MyReviewDto> getMyReview(@PathVariable Long id){  //내 리뷰 가져오기
        return memberService.getMyReview(id);}

    @GetMapping("/myPost/{id}")
    @ResponseBody
    public List<MyPostDto> getMyPost(@PathVariable Long id){  //내 상품 가져오기
        return memberService.getMyPost(id);}


    @GetMapping("/user/getinfo/{id}")
    @ResponseBody
    public UserInfoDto getUserInfo(@PathVariable Long id){  //유저 정보 가져오기
        UserInfoDto info = memberService.getUserInfo(id);
        return info;
    }

    @GetMapping("/ent/getinfo/{id}")
    @ResponseBody
    public EntInfoDto getEntInfo(@PathVariable String id){  //사업자 정보 가져오기
        Long getId = Long.parseLong(id);
        return memberService.getEntInfo(getId);
    }

    //관리자페이지
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")  //관리자페이지 가기
    public String adminMain() {
        return "auth/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @GetMapping("/admin/getEntList")  //사업자 리스트 가져오기
    public List<EntInfoDto> getEntList() {
        return memberService.getEntList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @GetMapping("/admin/getUserList")  //유저 리스트 가져오기
    public List<UserInfoDto> getUserList() {
        return memberService.getUserList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @PatchMapping("/admin/changeEntRoles/{id}")  //사업자 Role 바꾸기
    public void changeEntRoles(@PathVariable Long id, String email, Role role) {
        memberService.changeEntRoles(id, role);
        try {
            mailService.sendNoticeRole(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
