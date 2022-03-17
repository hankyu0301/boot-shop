package janghankyu.bootshop.dto.member.validator;

import janghankyu.bootshop.dto.member.SignUpRequestDto;
import janghankyu.bootshop.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class SignUpRequestValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignUpRequestDto dto = (SignUpRequestDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmpty(errors, "password", "required", "필수 정보 입니다.");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required", "필수 정보 입니다.");

        if(!dto.getPassword().isEmpty()) {
            if(!dto.isPasswordEqualToConfirmPassword()) {
                errors.rejectValue("confirmPassword", "nomatch", "비밀번호가 일치하지 않습니다.");
            }
        }

        if(memberRepository.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "duplicatedEmail","중복된 이메일 입니다.");
        }
        if(memberRepository.existsByNickname(dto.getNickname())) {
            errors.rejectValue("nickname", "duplicatedNickname", "중복된 닉네임 입니다.");
        }
    }
}
