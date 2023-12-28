package chathealth.chathealth.entity.member;

import chathealth.chathealth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
}
