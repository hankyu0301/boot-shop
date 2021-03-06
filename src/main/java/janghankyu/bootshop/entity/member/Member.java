package janghankyu.bootshop.entity.member;

import janghankyu.bootshop.dto.member.MemberUpdateDto;
import janghankyu.bootshop.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private String username;

    private String refreshToken;
    private Boolean emailAuth;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public Member(String email, String password, String nickname, String username, Boolean emailAuth, MemberRole memberRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.username = username;
        this.emailAuth = emailAuth;
        this.memberRole = memberRole;
    }

    public void updateMember(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}
