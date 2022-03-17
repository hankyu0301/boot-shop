package janghankyu.bootshop.repository.member;

import janghankyu.bootshop.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
