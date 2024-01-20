package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
//@SQLDelete(sql = "UPDATE message SET deleted_date = CURRENT_TIMESTAMP where message_id = ?") //h2 보낸 쪽에서만 삭제
@SQLDelete(sql = "UPDATE message SET deleted_date = SYSDATE where board_id = ?") //oracle 보낸 쪽에서만 삭제
public class Message extends BaseEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "message_id")
    private Long id;

    private Integer isRead;

    private String title;

    private String content;

    //보낸 쪽지에서 삭제
    private LocalDateTime deletedDate;

    //받은 쪽지에서 삭제
    private LocalDateTime deletedReceivedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    public void readMessage() {
        this.isRead = 1;
    }

    public void deleteReceivedMessage() {
        this.deletedReceivedDate = LocalDateTime.now();
    }
}