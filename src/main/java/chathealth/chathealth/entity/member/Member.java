package chathealth.chathealth.entity.member;

import chathealth.chathealth.constants.Role;
import chathealth.chathealth.entity.*;
import chathealth.chathealth.entity.board.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
//@Where(clause = "deleted_date != null")
public abstract class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String pw;
    private LocalDate birth;
    @Embedded
    private Address address;
    @Column(length = 1000)
    private String profile;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime deletedDate;

    @OneToMany(mappedBy = "user")
    private final List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<BoardComment> boardCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<Message> senderMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<Message> receiverMessages = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private final List<Subscription> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private final List<Subscription> followings = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Question> questions = new ArrayList<>();

    protected void update(Address address) {if(address != null) this.address = address;}
    public void updateProfile(String profile) {if(profile != null) this.profile = profile;}
    public void updatePw(String pw) {this.pw = pw;}
    public void withdraw(LocalDateTime now) {this.deletedDate = now;}
    public void changeRole(Role role) {this.role = role;}
}
