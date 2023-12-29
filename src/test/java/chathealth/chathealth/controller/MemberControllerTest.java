package chathealth.chathealth.controller;

import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.entity.member.*;
import chathealth.chathealth.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    MemberController memberController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 정보 조회")
    public void test1() throws Exception {
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
        // expected
        mockMvc.perform(get("/member/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("장성호"))
                .andExpect(jsonPath("$.nickname").value("짱공오일"))
                .andExpect(jsonPath("$.email").value("jjang051.hanmail.net"))
                .andExpect(jsonPath("$.grade").value("BRONZE"))
                .andExpect(jsonPath("$.profile").value("profile0321984u32895"))
                .andDo(print());
    }

    @Test
    public void test2() throws Exception {
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
        mockMvc.perform(get("/member/ent/{id}", ent.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ent.getId()))
                .andExpect(jsonPath("$.email").value("jjang051@google.com"))
                .andExpect(jsonPath("$.address.address").value("서울시 강남구"))
                .andExpect(jsonPath("$.address.addressDetail").value("123-123"))
                .andExpect(jsonPath("$.address.postcode").value(12345))
                .andExpect(jsonPath("$.birth").value("1995-03-21"))
                .andExpect(jsonPath("$.profile").value("profile0321984u32895"))
                .andExpect(jsonPath("$.role").value("WAITING_ENT"))
                .andExpect(jsonPath("$.ceo").value("장공오일"))
                .andExpect(jsonPath("$.entNo").value("1234-1234-1234"))
                .andExpect(jsonPath("$.company").value("중앙HTA"))
                .andDo(print());

    }

    @Test
    @DisplayName("유저 정보 조회 실패")
    public void test3() throws Exception {
//        given
        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);
        // expected
        mockMvc.perform(get("/member/user/{id}", 100L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("유저 정보 수정")
    public void test5() throws Exception {
        //given
        Address address = new Address("서울시 강남구", "123-123", 12345);

        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .address(address)
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);

        Address newAddress = new Address("대전시 유성구 가마로00길 11", "22-33", 98776);

        UserEditDto userEditDto = UserEditDto.builder()
                .id(user.getId())
                .nickname("조원우짱")
                .address(newAddress)
                .pw("1234")
                .newPw("jjang0512@")
                .newPwCheck("jjang0512@")
                .build();

        //expected
        mockMvc.perform(patch("/member/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("유저 정보 수정 실패 - 기존 비밀번호 불일치")
    public void test6() throws Exception {
//        given
        Address address = new Address("서울시 강남구", "123-123", 12345);
//
        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);

        Address newAddress = new Address("대전시 유성구 가마로00길 11", "22-33", 98776);

        UserEditDto userEditDto = UserEditDto.builder()
                .id(user.getId())
                .nickname("조원우짱")
                .address(newAddress)
                .pw("12345")
                .newPw("jjang0512@")
                .newPwCheck("jjang0512@")
                .build();

        //expected
        mockMvc.perform(patch("/member/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @DisplayName("유저 정보 수정 실패 - 새 비밀번호 불일치")
    public void test7() throws Exception {
//        given
        Address address = new Address("서울시 강남구", "123-123", 12345);
//
        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);

        Address newAddress = new Address("대전시 유성구 가마로00길 11", "22-33", 98776);

        UserEditDto userEditDto = UserEditDto.builder()
                .id(user.getId())
                .nickname("조원우짱")
                .address(newAddress)
                .pw("12345")
                .newPw("jjang0512@")
                .newPwCheck("jjang0512@1")
                .build();

        //expected
        mockMvc.perform(patch("/member/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @DisplayName("유저 정보 수정 실패 - 비밀번호 형식 불일치")
    public void test8() throws Exception {
//        given
        Address address = new Address("서울시 강남구", "123-123", 12345);
//
        Users user = Users.builder()
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .address(address)
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);

        Address newAddress = new Address("대전시 유성구 가마로00길 11", "22-33", 98776);

        UserEditDto userEditDto = UserEditDto.builder()
                .id(user.getId())
                .nickname("조원우짱")
                .address(newAddress)
                .pw("12345")
                .newPw("jjang0512")
                .newPwCheck("jjang0512")
                .build();

        //expected
        mockMvc.perform(patch("/member/user/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}