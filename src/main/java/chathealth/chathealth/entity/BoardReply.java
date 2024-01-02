package chathealth.chathealth.entity;

import chathealth.chathealth.entity.member.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class BoardReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "boardcomment_id")
    private BoardComment boardComment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String content;
    private LocalDateTime deletedDate;
}