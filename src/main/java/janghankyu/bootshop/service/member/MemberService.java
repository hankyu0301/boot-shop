package janghankyu.bootshop.service.member;

import janghankyu.bootshop.dto.email.EmailAuthRequestDto;
import janghankyu.bootshop.dto.member.MemberRegisterResponseDto;
import janghankyu.bootshop.dto.member.SignUpRequestDto;
import janghankyu.bootshop.dto.member.MemberUpdateDto;
import janghankyu.bootshop.entity.email.EmailAuth;
import janghankyu.bootshop.entity.member.Member;
import janghankyu.bootshop.entity.member.MemberRole;
import janghankyu.bootshop.exception.EmailAuthTokenNotFountException;
import janghankyu.bootshop.exception.MemberNotFoundException;
import janghankyu.bootshop.repository.email.EmailAuthCustomRepository;
import janghankyu.bootshop.repository.email.EmailAuthRepository;
import janghankyu.bootshop.repository.member.MemberRepository;
import janghankyu.bootshop.service.email.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder encoder;

    public MemberRegisterResponseDto save(SignUpRequestDto dto) {

        EmailAuth emailAuth = emailAuthRepository.save(new EmailAuth(dto.getEmail(), UUID.randomUUID().toString(), false));

        String enPw = encoder.encode(dto.getPassword());
        Member member = memberRepository.save(new Member(dto.getEmail(), enPw, dto.getNickname(), dto.getUsername(),false, MemberRole.USER));
        emailSenderService.sendEmail(emailAuth.getEmail(), emailAuth.getAuthToken());

        return new MemberRegisterResponseDto(member.getId(), member.getEmail(), emailAuth.getAuthToken());
    }

    public void confirmEmail(EmailAuthRequestDto requestDto) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(requestDto.getEmail(), requestDto.getAuthToken(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFountException::new);
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        emailAuth.useToken();
        member.emailVerifiedSuccess();
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

//    public boolean checkEmailDuplicate(String email) {
//        return memberRepository.existsByEmail(email);
//    }
//
//    public boolean checkNicknameDuplicate(String nickname) {
//        return memberRepository.existsByNickname(nickname);
//    }


}
