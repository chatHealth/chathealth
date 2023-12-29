package chathealth.chathealth.controller;

import chathealth.chathealth.entity.member.*;
import chathealth.chathealth.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired MemberController memberController;

    @Autowired MockMvc mockMvc;

    @Autowired MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

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
    public void test2() throws Exception{
        Ent ent = Ent.builder()
                .id(2L)
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
                .andExpect(jsonPath("$.id").value(2L))
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
    public void test3() throws Exception{
        //given
        Users user = Users.builder()
                .id(3L)
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