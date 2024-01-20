package chathealth.chathealth.repository.message;

import chathealth.chathealth.entity.Message;
import chathealth.chathealth.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    Long countByReceiverAndIsRead(Member receiver, Integer isRead);

    @Query("select m from Message m join fetch m.sender where m.id = :id")
    Optional<Message> findFetchById(Long id);
}