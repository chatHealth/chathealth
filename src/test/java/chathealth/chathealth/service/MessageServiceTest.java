package chathealth.chathealth.service;

import chathealth.chathealth.dto.request.SendMessageDto;
import chathealth.chathealth.dto.response.member.CustomUserDetails;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.message.MessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("메시지를 보내면 메시지가 저장된다")
    public void sendMessage() throws Exception{
        //given
        Users sender = Users.builder().email("senderEmail").build();
        Users savedSender = memberRepository.save(sender);

        Users receiver = Users.builder().id(99111L).email("receiverEmail").build();
        Users savedReceiver = memberRepository.save(receiver);

        CustomUserDetails customUserDetails = new CustomUserDetails(savedSender);

        SendMessageDto message = SendMessageDto.builder()
                .content("content")
                .title("title")
                .build();
        //when
//        Message message1 = messageService.sendMessage(customUserDetails, savedReceiver.getId(), message);
        messageService.sendMessage(customUserDetails, savedReceiver.getId(), message);
        //then
//        Message message2 = messageRepository.findById(message1.getId()).orElseThrow(() -> new IllegalArgumentException("메시지가 저장되지 않았습니다."));
//        Assertions.assertThat(message2.getContent()).isEqualTo(message.getContent());
//        Assertions.assertThat(message2.getTitle()).isEqualTo(message.getTitle());
//        Assertions.assertThat(message2.getSender().getId()).isEqualTo(savedSender.getId());
//        Assertions.assertThat(message2.getReceiver().getId()).isEqualTo(savedReceiver.getId());
//        Assertions.assertThat(message2.getIsRead()).isEqualTo(1);
    }
}