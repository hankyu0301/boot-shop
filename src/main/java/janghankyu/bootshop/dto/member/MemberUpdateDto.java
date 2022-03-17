package janghankyu.bootshop.dto.member;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String nickname;
    private String password;
    private String confirmPassword;

}
