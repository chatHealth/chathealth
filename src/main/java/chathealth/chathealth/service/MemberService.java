package chathealth.chathealth.service;

import chathealth.chathealth.dto.response.UserInfoDto;
import chathealth.chathealth.entity.member.Users;
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
}
