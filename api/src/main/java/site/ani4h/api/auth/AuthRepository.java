package site.ani4h.api.auth;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository {
    public void create(AuthRegister authRegister);
    public Auth findByEmail(String email);
}
