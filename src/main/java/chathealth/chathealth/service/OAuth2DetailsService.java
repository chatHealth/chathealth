package chathealth.chathealth.service;


import chathealth.chathealth.dto.response.CustomUserDetails;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.social.KakaoUserInfo;
import chathealth.chathealth.social.SocialUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static chathealth.chathealth.constants.Grade.*;
import static chathealth.chathealth.constants.Role.ROLE_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); //OAuth2 에서 넘겨준 정보들 가져오기

        Map<String,Object> oAuth2UserInfo = (Map)oAuth2User.getAttributes(); //정보들 Map으로 담기

        SocialUserInfo socialUserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId(); //정보 넘겨준 회사이름 받아오기

        if(provider.equals("kakao")) { //정보 넘겨준 회사가 카카오면
            socialUserInfo = new KakaoUserInfo(oAuth2UserInfo); //카카오 유저인포 생성
        }

        assert socialUserInfo != null : "이메일 없음";
        String email = socialUserInfo.getEmail();
        String name = socialUserInfo.getName();
        String userId = socialUserInfo.getProviderId();
        log.info(userId);
        Role role = ROLE_USER;
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        Users returnMember = null;

        Optional<Member> foundMember =  memberRepository.findByEmail(userId);
        if(foundMember.isPresent()) {
            returnMember = (Users) foundMember.get();
        } else {
            returnMember = Users.builder()
                    .pw(password)
                    .role(role)
                    .name(name)
                    .email(userId)
                    .grade(BRONZE)
                    .build();
            memberRepository.save(returnMember);
        }
        return new CustomUserDetails(returnMember,oAuth2User.getAttributes());
    }
}
