package janghankyu.bootshop.controller.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(String email) {

        String token = "";

        return ResponseEntity.ok(token);
    }
}
