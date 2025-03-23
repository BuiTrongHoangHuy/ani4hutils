package site.ani4h.auth.user;

import site.ani4h.shared.common.Paging;

import java.util.List;

public interface UserRepository {
     void create(UserCreate userCreate);
     User getUserById(int id);
     List<User> getUsers(Paging paging);
}
