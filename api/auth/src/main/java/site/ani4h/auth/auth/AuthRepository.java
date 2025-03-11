package site.ani4h.auth.auth;


import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository {
    public void create(AuthRegister authRegister);
    public Auth findByEmail(String email);
}
