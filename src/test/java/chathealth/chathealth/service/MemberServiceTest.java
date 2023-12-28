package chathealth.chathealth.service;

import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Role;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("유저 정보 조회")
    public void test1() throws Exception{
        //given
        Users user1 = Users.builder()
                .id(1L)
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user1);
        //when
        UserInfoDto userInfo = memberService.getUserInfo(1L);

        //then

        assertThat(userInfo.getId()).isEqualTo(1L);
        assertThat(userInfo.getName()).isEqualTo("장성호");
        assertThat(userInfo.getNickname()).isEqualTo("짱공오일");
        assertThat(userInfo.getEmail()).isEqualTo("jjang051.hanmail.net");
        assertThat(userInfo.getGrade()).isEqualTo(Grade.valueOf("BRONZE"));
        assertThat(userInfo.getProfile()).isEqualTo("profile0321984u32895");
    }

    @Test
    @DisplayName("유저 정보 조회 실패")
    public void test2() throws Exception{
        //given
        Users user1 = Users.builder()
                .id(1L)
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user1);
        //expected
        assertThrows(UserNotFound.class, () -> memberService.getUserInfo(2L));

    }
}