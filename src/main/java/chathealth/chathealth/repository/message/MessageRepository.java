package chathealth.chathealth.repository.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    Long countByReceiverAndIsRead(Member receiver, Integer isRead);

}