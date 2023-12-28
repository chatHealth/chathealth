package chathealth.chathealth.controller;

import chathealth.chathealth.entity.member.Grade;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired MemberController memberController;

    @Autowired MockMvc mockMvc;

    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("유저 정보 조회")
    public void test1() throws Exception{
        //given
        Users user = Users.builder()
                .id(1L)
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
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("장성호"))
                .andExpect(jsonPath("$.nickname").value("짱공오일"))
                .andExpect(jsonPath("$.email").value("jjang051.hanmail.net"))
                .andExpect(jsonPath("$.grade").value("BRONZE"))
                .andExpect(jsonPath("$.profile").value("profile0321984u32895"))
                .andDo(print());
    }
    @Test
    @DisplayName("유저 정보 조회 실패")
    public void test2() throws Exception{
        //given
        Users user = Users.builder()
                .id(1L)
                .name("장성호")
                .nickname("짱공오일")
                .email("jjang051.hanmail.net")
                .pw("1234")
                .grade(Grade.valueOf("BRONZE"))
                .profile("profile0321984u32895")
                .build();

        memberRepository.save(user);
        // expected

    }
}