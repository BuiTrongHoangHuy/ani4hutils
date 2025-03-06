package site.ani4h.api.middleware.jwt_spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.api.auth.Auth;
import site.ani4h.api.auth.AuthRepository;

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
            return UserDetailsImpl.build(auth.getId(),auth.getUserId(),auth.getEmail(),auth.getPassword(),auth.getSalt());
        }
    }
}
