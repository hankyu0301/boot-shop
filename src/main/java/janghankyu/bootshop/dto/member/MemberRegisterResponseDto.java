package janghankyu.bootshop.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRegisterResponseDto {

    private Long id;
    private String email;
    private String authToken;

    public MemberRegisterResponseDto(Long id, String email, String authToken) {
        this.id = id;
        this.email = email;
        this.authToken = authToken;
    }
}
