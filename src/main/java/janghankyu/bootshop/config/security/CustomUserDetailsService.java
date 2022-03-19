package janghankyu.bootshop.config.security;

import janghankyu.bootshop.entity.member.Member;
import janghankyu.bootshop.exception.EmailNotAuthenticatedException;
import janghankyu.bootshop.exception.MemberNotFoundException;
import janghankyu.bootshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        if(!member.getEmailAuth()) {
            throw new EmailNotAuthenticatedException("이메일 인증이 필요합니다.");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(member.getMemberRole().toString()));

        return new CustomUserDetails(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getUsername(),
                member.getPassword(),
                authorities
        );
    }
}
