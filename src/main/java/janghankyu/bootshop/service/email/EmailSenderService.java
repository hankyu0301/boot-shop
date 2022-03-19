package janghankyu.bootshop.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "finebears7@gmail.com";

    //난수생성 후 email 발
    @Async
    public void sendEmail(String email, String authToken){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(EmailSenderService.FROM_ADDRESS);
        message.setSubject("회원가입 이메일 인증");
        message.setText("http://localhost:8080/member/confirm-email?email="+email+"&authToken="+authToken);

        javaMailSender.send(message);
    }


}
