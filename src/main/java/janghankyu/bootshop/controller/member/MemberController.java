package janghankyu.bootshop.controller.member;

import janghankyu.bootshop.dto.email.EmailAuthRequestDto;
import janghankyu.bootshop.dto.member.SignUpRequestDto;
import janghankyu.bootshop.dto.member.validator.SignUpRequestValidator;
import janghankyu.bootshop.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final SignUpRequestValidator validator;

    @GetMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("signUpRequestDto", new SignUpRequestDto());
        return "member/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute SignUpRequestDto dto, Errors errors) {

        validator.validate(dto, errors);

        if(errors.hasErrors()) {
            return "member/signUp";
        }

        memberService.save(dto);
        return "member/checkEmail";
    }

    @GetMapping("/confirm-email")
    public String confirmEmail(@ModelAttribute EmailAuthRequestDto requestDto) {
        memberService.confirmEmail(requestDto);
        return "redirect:/";
    }

//    @PostMapping("/checkEmail")
//    public @ResponseBody ResponseEntity<Boolean> checkEmail(String email) {
//        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
//    }
//
//    @PostMapping("/checkNickname")
//    public @ResponseBody ResponseEntity<Boolean> checkNickname(String nickname) {
//        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
//    }
}
