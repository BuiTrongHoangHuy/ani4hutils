package site.ani4h.auth.user;

import org.springframework.stereotype.Service;
import site.ani4h.auth.auth.AuthService;
import site.ani4h.auth.auth.entity.Auth;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }


    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
    public List<User> getUsers(Paging paging) {
        return userRepository.getUsers(paging);
    }

    public Uid getUserIdByEmail(String email) {
        Auth auth = authService.findByEmail(email);
        if (auth == null) {
            return null;
        }
        return auth.getUserId();
    }
}
