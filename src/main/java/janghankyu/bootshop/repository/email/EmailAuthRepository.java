package janghankyu.bootshop.repository.email;

import janghankyu.bootshop.entity.email.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth,Long>,EmailAuthCustomRepository {
}
