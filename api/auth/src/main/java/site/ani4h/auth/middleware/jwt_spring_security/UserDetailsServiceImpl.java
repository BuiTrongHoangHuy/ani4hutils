package site.ani4h.auth.middleware.jwt_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.auth.auth.entity.Auth;
import site.ani4h.auth.auth.AuthRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthRepository authRepository;
    @Autowired
    public UserDetailsServiceImpl(AuthRepository authRepository) {
        super();
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException{
        Auth auth = this.authRepository.findByEmail(email);
        if (auth == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        } else {
            return UserDetailsImpl.build(auth.getId().getLocalId(),auth.getUserId(),auth.getEmail(),auth.getPassword(),auth.getSalt());
        }
    }
}
