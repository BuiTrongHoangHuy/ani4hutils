package site.ani4h.api.user;

public interface UserRepository {
     void create(UserCreate userCreate);
     User getUserById(int id);
}
