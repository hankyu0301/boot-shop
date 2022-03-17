package janghankyu.bootshop.service.member;

import janghankyu.bootshop.dto.member.SignUpRequestDto;
import janghankyu.bootshop.dto.member.MemberUpdateDto;
import janghankyu.bootshop.entity.member.Member;
import janghankyu.bootshop.entity.member.MemberRole;
import janghankyu.bootshop.exception.MemberNotFoundException;
import janghankyu.bootshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void save(SignUpRequestDto dto) {
        String enPw = encoder.encode(dto.getPassword());
        memberRepository.save(new Member(dto.getEmail(), enPw, dto.getNickname(), dto.getUsername(), MemberRole.USER));
    }

    public void update(Long id,MemberUpdateDto dto) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        String enPw = encoder.encode(dto.getPassword());
        member.updateMember(enPw, dto.getNickname());
    }

    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(id);
    }

    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


}
