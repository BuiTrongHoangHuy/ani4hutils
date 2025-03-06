package site.ani4h.api.middleware.jwt_spring_security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    public static UserDetailsImpl build(Integer id, Integer userId, String email, String password, String salt) {
        return new UserDetailsImpl(
                id,
                userId,
                email,
                password,
                salt
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    public int getUserId() {
        return userId;
    }
}
