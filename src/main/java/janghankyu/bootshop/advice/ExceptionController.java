package janghankyu.bootshop.advice;

import janghankyu.bootshop.exception.EmailAlreadyExistsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String emailAlreadyExistsException(EmailAlreadyExistsException e, Model model) {
        model.addAttribute("emailError", "이미 회원 가입된 이메일 입니다.");
        return "/signUp";
    }

}
