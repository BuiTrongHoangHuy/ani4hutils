package site.ani4h.auth.auth;


import org.springframework.stereotype.Repository;
import site.ani4h.auth.auth.entity.Auth;
import site.ani4h.auth.auth.entity.AuthRegister;


@Repository
public interface AuthRepository {
    public void create(AuthRegister authRegister);
    public Auth findByEmail(String email);
}
