package chathealth.chathealth.service;

import chathealth.chathealth.controller.NotificationController;
import chathealth.chathealth.dto.request.SendMessageDto;
import chathealth.chathealth.dto.response.*;
import chathealth.chathealth.dto.response.message.MessageReceiveResponse;
import chathealth.chathealth.dto.response.message.MessageReceiveResponseDetail;
import chathealth.chathealth.dto.response.message.MessageSendResponse;
import chathealth.chathealth.dto.response.message.MessageSendResponseDetail;
import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Member;
import chathealth.chathealth.entity.member.Users;
import chathealth.chathealth.exception.MessageNotFound;
import chathealth.chathealth.exception.NotPermitted;
import chathealth.chathealth.exception.UserNotFound;
import chathealth.chathealth.repository.MemberRepository;
import chathealth.chathealth.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final NotificationController notificationController;

    //쪽지 보내기
    public void sendMessage(CustomUserDetails customUserDetails, Long receiverId, SendMessageDto sendMessageDto) {


        Member member = memberRepository.findByEmail(customUserDetails.getLoggedMember().getEmail())
                .orElseThrow(UserNotFound::new);

        Users sender;
        if(!(member instanceof Users)){
            throw new NotPermitted("개인회원만 쪽지를 보낼 수 있습니다.");
        }else {
            sender = (Users) member;
        }

        if(customUserDetails.getLoggedMember().getId().equals(receiverId)){
            throw new NotPermitted("자기 자신에게 쪽지를 보낼 수 없습니다.");
        }

        Member receiver = memberRepository.findById(receiverId).orElseThrow(UserNotFound::new);

        messageRepository.save(sendMessageDto.toEntity(member, receiver));

        notificationController.dispatchNewMessage(receiver.getEmail(),
                sender.getNickname()!=null? sender.getNickname() : sender.getName() + "님에게 새로운 쪽지가 도착했습니다.");
    }

    //받은 쪽지함
    public Page<MessageReceiveResponse> receivedMessages(CustomUserDetails customUserDetails, Pageable pageable) {
        Member me = memberRepository.findByEmail(customUserDetails.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);
        return messageRepository.receivedMessages(me, pageable);
    }

    //보낸 쪽지함
    public Page<MessageSendResponse> sendMessages(CustomUserDetails customUserDetails, Pageable pageable) {
        Member me = memberRepository.findByEmail(customUserDetails.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);
        return messageRepository.sendMessages(me, pageable);
    }

    //쪽지 삭제(보낸 사람 쪽에서만 삭제)
    public void deleteSendMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    //쪽지 삭제 (받은 사람 쪽에서만 삭제)
    @Transactional
    public void deleteReceivedMessage(Long messageId) {
        Message message = messageRepository.findFetchById(messageId).orElseThrow(MessageNotFound::new);
        message.deleteReceivedMessage();
    }
    //보낸 쪽지 읽기

    public MessageSendResponseDetail getSendMessage(Long messageId) {
        Message message = messageRepository.findReceiverFetchById(messageId).orElseThrow(MessageNotFound::new);

        Users receiver;
        if (message.getReceiver() instanceof Users) {
            receiver = (Users) message.getReceiver();
        } else {
            throw new UserNotFound();
        }

        return MessageSendResponseDetail.get(message, receiver);
    }
    //받은 쪽지 읽기 + 쪽지 읽음 처리

    @Transactional
    public MessageReceiveResponseDetail getReceivedMessage(Long messageId) {
        Message message = messageRepository.findFetchById(messageId).orElseThrow(MessageNotFound::new);

        if (message.getIsRead() == 0) {
            System.out.println("읽지 않은 쪽지 읽음 처리");
            message.readMessage();
        }

        Users sender;

        if (message.getSender() instanceof Users) {
            sender = (Users) message.getSender();
        } else {
            throw new UserNotFound();
        }

        return MessageReceiveResponseDetail.get(message, sender);
    }
    //받은 쪽지 중 읽지 않은 쪽지 개수

    public Long getUnreadMessageCount(CustomUserDetails customUserDetails) {
        Member member = memberRepository.findByEmail(customUserDetails.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);
        if (member instanceof Users) {
            return messageRepository.countByReceiverAndIsRead(member, 0);
        } else {
            throw new UserNotFound();
        }
    }
    //받은 쪽지 중 읽지 않은 쪽지 리스트

    public Page<MessageReceiveResponse> getUnreadMessages(CustomUserDetails customUserDetails, Pageable pageable) {
        Member member = memberRepository.findByEmail(customUserDetails.getLoggedMember().getEmail()).orElseThrow(UserNotFound::new);
        if (member instanceof Users) {
            return messageRepository.getUnreadMessages(member, pageable);
        } else {
            throw new UserNotFound();
        }
    }

    public boolean isSender(Authentication authentication, Long messageId) {
        String currentUsername = authentication.getName();
        String senderEmail = messageRepository.findFetchById(messageId)
                .orElseThrow(MessageNotFound::new).getSender().getEmail();
        return currentUsername.equals(senderEmail);
    }

    public boolean isReceiver(Authentication authentication, Long messageId) {
        String currentUsername = authentication.getName();
        String receiverEmail = messageRepository.findFetchById(messageId)
                .orElseThrow(MessageNotFound::new).getReceiver().getEmail();
        return currentUsername.equals(receiverEmail);
    }
}