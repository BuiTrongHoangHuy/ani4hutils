package site.ani4h.api.user;

import org.springframework.stereotype.Service;

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
    public List<User> getUsers() {
        return userRepository.getUsers();
    }
}
