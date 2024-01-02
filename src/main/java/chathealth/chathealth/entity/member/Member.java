package chathealth.chathealth.entity.member;

import chathealth.chathealth.entity.BaseEntity;
import chathealth.chathealth.entity.BoardComment;
import chathealth.chathealth.entity.BoardReply;
import chathealth.chathealth.entity.borad.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = PROTECTED)
public abstract class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String pw;
    private LocalDate birth;
    @Embedded
    private Address address;
    @Column(length = 1000)
    private String profile;
    private Role role;
    private int report;
    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "user")
    private final List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<BoardComment> boardCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<BoardReply> boardReplyList = new ArrayList<>();

    protected void update(Address address, String pw) {
        this.address = address;
        this.pw = pw;
    }

}
