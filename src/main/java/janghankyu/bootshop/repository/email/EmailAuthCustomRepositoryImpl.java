package janghankyu.bootshop.repository.email;

import com.querydsl.jpa.impl.JPAQueryFactory;
import janghankyu.bootshop.entity.email.EmailAuth;
import janghankyu.bootshop.entity.email.QEmailAuth;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class EmailAuthCustomRepositoryImpl  implements EmailAuthCustomRepository{

    JPAQueryFactory jpaQueryFactory;

    public EmailAuthCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime currentTime) {
        EmailAuth emailAuth = jpaQueryFactory
                .selectFrom(QEmailAuth.emailAuth)
                .where(QEmailAuth.emailAuth.email.eq(email),
                        QEmailAuth.emailAuth.authToken.eq(authToken),
                        QEmailAuth.emailAuth.expireDate.goe(currentTime),
                        QEmailAuth.emailAuth.expired.eq(false))
                .fetchFirst();

        return Optional.ofNullable(emailAuth);
    }
}