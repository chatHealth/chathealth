package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntEditDto;
import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.PostLikeDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Address;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.PostLikeRepository;
import chathealth.chathealth.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.User;
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

    //@PreAuthorize("hasRole('USER')") //USER 롤 가지고 있는 사람만 메서드 실행 가능
    @GetMapping("/user/{id}")  //개인 마이페이지로 이동
    public String getUserInfo(@PathVariable Long id, Model model) {
        UserInfoDto userInfoDto = memberService.getUserInfo(id);
        model.addAttribute("userInfo", userInfoDto);
       return "member/user-info";

    }

    @GetMapping("/ent/{id}")  //사업자 마이페이지로 이동
    public String getEntInfo(@PathVariable Long id,Model model) {
        EntInfoDto entInfoDto = memberService.getEntInfo(id);
        model.addAttribute("entInfo",entInfoDto);
        return "member/ent-info";
    }

    @Transactional
    @ResponseBody
    @PatchMapping("/user/{id}") //개인도 사업자도 프로필은 여기서 바꾼다
    public Map<String,String> updateUserProfile(@PathVariable Long id, MultipartFile changeProfile) {
        memberService.updateProfile(id, changeProfile);
        Map<String, String> resultMap = new HashMap<>();

        resultMap.put("isUpdated","isUpdated");
        return resultMap;
    }


    @Transactional
    @ResponseBody
    @PatchMapping("/user/updateInfo")
    public Map<String,String> updateUserInfo(UserEditDto userEditDto) {
        log.info("컨트롤러 들어왔다~");
        Address address = Address.builder()
                .address(userEditDto.getFrontAddress())
                .addressDetail(null)
                .build();
        log.info(address.getAddress());
        log.info(address.getAddressDetail());
        userEditDto.setAddress(address);
        log.info(String.valueOf(userEditDto));
        memberService.updateUserInfo(userEditDto.getId(), userEditDto);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("isUpdated","isUpdated");
        return resultMap;
    }

    @Transactional
    @ResponseBody
    @PatchMapping("/ent/updateInfo")
    public void updateEnt(@PathVariable Long id, @RequestBody @Valid EntEditDto entEditDto) {
        memberService.updateEntInfo(id, entEditDto);
    }

    @GetMapping("/post-like/{id}")
    @ResponseBody
    public List<PostLikeDto> getPostLike(@PathVariable Long id){
        return memberService.getPostLike(id);}
}
