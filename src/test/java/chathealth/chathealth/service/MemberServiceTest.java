package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.*;
import chathealth.chathealth.exception.PasswordNotEqual;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("유저 정보 조회")
    public void test1() throws Exception{
        //given
        Address address = new Address("서울시 강남구", "123-123", 12345);

        Users user1 = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .address(address)
                .build();

        memberRepository.save(user1);
        //when
        UserInfoDto userInfo = memberService.getUserInfo(user1.getId());

        //then

        assertThat(userInfo.getId()).isEqualTo(user1.getId());
        assertThat(userInfo.getName()).isEqualTo("장성호");
        assertThat(userInfo.getNickname()).isEqualTo("짱공오일");
        assertThat(userInfo.getEmail()).isEqualTo("jjang051.hanmail.net");
        assertThat(userInfo.getGrade()).isEqualTo(Grade.valueOf("BRONZE"));
        assertThat(userInfo.getProfile()).isEqualTo("profile0321984u32895");
        assertThat(userInfo.getAddress().getAddress()).isEqualTo("서울시 강남구");
        assertThat(userInfo.getAddress().getAddressDetail()).isEqualTo("123-123");
        assertThat(userInfo.getAddress().getPostcode()).isEqualTo(12345);
    }

    @Test
    @DisplayName("유저 정보 조회 실패")
    public void test2() throws Exception{
        //given
        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);
        //expected
        assertThrows(UserNotFound.class, () -> memberService.getUserInfo(100L));
    }

    @Test
    @DisplayName("사업자 정보 조회")
    public void test3() throws Exception{
        //given
        Ent ent = Ent.builder()
                .email("jjang051@google.com")
                .address(new Address("서울시 강남구", "123-123", 12345))
                .birth(LocalDate.of(1995, 3, 21))
                .profile("profile0321984u32895")
                .role(Role.valueOf("WAITING_ENT"))
                .ceo("장공오일")
                .entNo("1234-1234-1234")
                .company("중앙HTA")
                .build();

        memberRepository.save(ent);
        //when
        EntInfoDto entInfo = memberService.getEntInfo(ent.getId());

        //then
        assertThat(entInfo.getId()).isEqualTo(ent.getId());
        assertThat(entInfo.getEmail()).isEqualTo("jjang051@google.com");
        assertThat(entInfo.getAddress().getAddress()).isEqualTo("서울시 강남구");
        assertThat(entInfo.getAddress().getAddressDetail()).isEqualTo("123-123");
        assertThat(entInfo.getAddress().getPostcode()).isEqualTo(12345);
        assertThat(entInfo.getBirth()).isEqualTo(LocalDate.of(1995, 3, 21));
        assertThat(entInfo.getProfile()).isEqualTo("profile0321984u32895");
        assertThat(entInfo.getRole()).isEqualTo(Role.valueOf("WAITING_ENT"));
        assertThat(entInfo.getCeo()).isEqualTo("장공오일");
        assertThat(entInfo.getEntNo()).isEqualTo("1234-1234-1234");
        assertThat(entInfo.getCompany()).isEqualTo("중앙HTA");
    }

    @Test
    @DisplayName("사업자 정보 조회 실패")
    public void test4() throws Exception{
        //given
        Ent ent = Ent.builder()
                .email("jjang051@google.com")
                .address(new Address("서울시 강남구", "123-123", 12345))
                .birth(LocalDate.of(1995, 3, 21))
                .profile("profile0321984u32895")
                .role(Role.valueOf("WAITING_ENT"))
                .ceo("장공오일")
                .entNo("1234-1234-1234")
                .company("중앙HTA")
                .build();

        memberRepository.save(ent);
        //expected
        assertThrows(UserNotFound.class, () -> memberService.getEntInfo(100L));
    }

    @Test
    @DisplayName("유저 정보 수정")
    public void test5() throws Exception{
        //given
        Address address = new Address("서울시 강남구", "123-123", 12345);

        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .address(address)
                .build();

        memberRepository.save(user);
        //when
        UserEditDto updateUser = UserEditDto.builder()
                .nickname("공짱오일")
                .address(new Address("대전시 유성구 가마로00길 11", "22-33", 98776))
                .pw("1234")
                .newPw("jjang0512")
                .newPwCheck("jjang0512")
                .build();

        memberService.updateUserInfo(user.getId(), updateUser);

        Users userInfo = (Users)memberRepository.findById(user.getId()).orElseThrow(
                UserNotFound::new
        );

        //then
        assertThat(userInfo.getId()).isEqualTo(user.getId());
        assertThat(userInfo.getName()).isEqualTo("장성호");
        assertThat(userInfo.getNickname()).isEqualTo("공짱오일");
        assertThat(userInfo.getEmail()).isEqualTo("jjang051.hanmail.net");
        assertThat(userInfo.getGrade()).isEqualTo(Grade.valueOf("BRONZE"));
        assertThat(userInfo.getProfile()).isEqualTo("profile0321984u32895");
        assertThat(userInfo.getAddress().getAddress()).isEqualTo("대전시 유성구 가마로00길 11");
        assertThat(userInfo.getAddress().getAddressDetail()).isEqualTo("22-33");
        assertThat(userInfo.getAddress().getPostcode()).isEqualTo(98776);
        assertThat(userInfo.getPw()).isEqualTo("jjang0512");
    }
    @Test
    @DisplayName("유저 정보 수정 실패 - 새로운 비밀번호 불일치")
    public void test6() throws Exception{
        //given
        Address address = new Address("서울시 강남구", "123-123", 12345);

        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .address(address)
                .build();

        memberRepository.save(user);
        //when
        UserEditDto updateUser = UserEditDto.builder()
                .nickname("")
                .address(new Address("대전시 유성구 가마로00길 11", "22-33", 98776))
                .pw("1234")
                .newPw("jjang0512")
                .newPwCheck("jjang05132")
                .build();

        //then
        assertThatThrownBy(() -> memberService.updateUserInfo(user.getId(), updateUser))
                .isInstanceOf(PasswordNotEqual.class)
                .hasMessageContaining("새로운 비밀번호가 일치하지 않습니다.");
    }
    @Test
    @DisplayName("유저 정보 수정 실패 - 기존 비밀번호 불일치")
    public void test7() throws Exception{
        //given
        Address address = new Address("서울시 강남구", "123-123", 12345);

        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .address(address)
                .build();

        memberRepository.save(user);
        //when
        UserEditDto updateUser = UserEditDto.builder()
                .nickname("")
                .address(new Address("대전시 유성구 가마로00길 11", "22-33", 98776))
                .pw("12345")
                .newPw("jjang0512")
                .newPwCheck("jjang0512")
                .build();

        //then
        assertThatThrownBy(() -> memberService.updateUserInfo(user.getId(), updateUser))
                .isInstanceOf(PasswordNotEqual.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }
}