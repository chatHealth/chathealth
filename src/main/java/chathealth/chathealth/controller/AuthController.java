package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.EntJoinDto;
import chathealth.chathealth.dto.request.UserJoinDto;
import chathealth.chathealth.entity.member.Address;


import chathealth.chathealth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    //로그인
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProcess(){
        return "redirect:/";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(){
        return "auth/logout";
    }

    //가입
    @GetMapping("/selection") //개인회원가입창 진입
    public String selection(Model model) {
        return "auth/selection";
    }

    @GetMapping("/userjoin") //개인회원가입창 진입
    public String userJoin(Model model) {
        return "auth/user-join";
    }

    @PostMapping("/userjoin") //개인회원가입 처리
    public String userJoin(@ModelAttribute UserJoinDto userJoinDto, MultipartFile profile) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(userJoinDto.getPostcode())
                .address(userJoinDto.getFrontAddress())
                .addressDetail(userJoinDto.getAddressDetail())
                .build();
        log.info(userJoinDto.getPostcode());

        //service에 던질 DTO 빌드
        userJoinDto.setAddress(addressEntity);
        authService.userJoin(userJoinDto);

        return "redirect:/auth/user-join";
    }

    @GetMapping("/entjoin") //사업자회원가입창 진입
    public String entJoin(Model model) {
        return "auth/ent-join";
    }

    @PostMapping("/entjoin") //사업자회원가입 처리
    public String entJoin(@ModelAttribute EntJoinDto entJoinDto, MultipartFile profile) {
        //주소 객체화
        Address addressEntity = Address.builder()
                .postcode(entJoinDto.getPostcode())
                .address(entJoinDto.getFrontAddress())
                .addressDetail(entJoinDto.getAddressDetail())
                .build();
        log.info(String.valueOf(addressEntity));


        //service에 던질 DTO 빌드
        entJoinDto.setAddress(addressEntity);

        authService.entJoin(entJoinDto);
        return "redirect:/auth/ent-join";
    }


    //관리자페이지
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminMain(Model model) {
        return "auth/admin-manage-user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public String admin(Model model) {
        return "auth/admin-manage-user";
    }

    @PostMapping("/confirmEmail") //아이디 중복체크
    @ResponseBody
    public Map<String, Boolean> idCheck(@RequestParam("email") String email) {
        boolean isExist = authService.confirmEmail(email);
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isExist", isExist);
        return resultMap;

    }
}