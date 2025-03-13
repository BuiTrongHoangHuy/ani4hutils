package site.ani4h.auth.user;

import org.springframework.stereotype.Service;
import site.ani4h.share.common.Paging;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
    public List<User> getUsers(Paging paging) {
        return userRepository.getUsers(paging);
    }
}
