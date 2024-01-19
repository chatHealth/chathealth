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
@SQLDelete(sql = "UPDATE message SET deleted_date = CURRENT_TIMESTAMP where board_id = ?") //h2
//@SQLDelete(sql = "UPDATE message SET deleted_date = SYSDATE where board_id = ?") //oracle
public class Message extends BaseEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "message_id")
    private Long id;

    private Integer isRead;

    private String title;

    private String content;

    private LocalDateTime deletedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    public void getReadMessage() {
        this.isRead = 1;
    }
}