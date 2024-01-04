package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Message extends BaseEntity{

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "message_id")
    private Long id;

    private Integer isRead;

    private LocalDateTime deletedDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;
}