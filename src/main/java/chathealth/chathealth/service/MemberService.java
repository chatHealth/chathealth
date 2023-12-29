package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.UserEditDto;
import chathealth.chathealth.dto.response.EntInfoDto;
import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Ent;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.PasswordNotEqualException;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public UserInfoDto getUserInfo(Long id) {
        Users findUser = (Users) memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );

        return UserInfoDto.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .nickname(findUser.getNickname())
                .email(findUser.getEmail())
                .profile(findUser.getProfile())
                .birth(findUser.getBirth())
                .deletedDate(findUser.getDeletedDate())
                .grade(findUser.getGrade())
                .address(findUser.getAddress())
                .build();
    }

    public EntInfoDto getEntInfo(Long id) {
        Ent findEnt = (Ent) memberRepository.findById(id).orElseThrow(
                UserNotFound::new

        );

        return EntInfoDto.builder()
                .id(findEnt.getId())
                .email(findEnt.getEmail())
                .address(findEnt.getAddress())
                .birth(findEnt.getBirth())
                .profile(findEnt.getProfile())
                .deletedDate(findEnt.getDeletedDate())
                .role(findEnt.getRole())
                .ceo(findEnt.getCeo())
                .company(findEnt.getCompany())
                .entNo(findEnt.getEntNo())
                .build();
    }

    public void updateUserInfo(Long id, UserEditDto userEditDto) {
        Users findUser = (Users) memberRepository.findById(id).orElseThrow(
                UserNotFound::new
        );
        if (!findUser.getPw().equals(userEditDto.getPw())) {
            throw new PasswordNotEqualException();
        } else if (!userEditDto.getNewPw().equals(userEditDto.getNewPwCheck())) {
            throw new PasswordNotEqualException("새로운 비밀번호가 일치하지 않습니다.");
        }

        findUser.update(userEditDto.getNickname() != null ? userEditDto.getNickname() : findUser.getNickname(),
                userEditDto.getAddress() != null? userEditDto.getAddress() : findUser.getAddress(),
                userEditDto.getNewPw());
    }
}
