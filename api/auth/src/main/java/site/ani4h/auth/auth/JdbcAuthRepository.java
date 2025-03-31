package site.ani4h.auth.auth;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.ani4h.auth.auth.entity.Auth;
import site.ani4h.auth.auth.entity.AuthRegister;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class JdbcAuthRepository implements AuthRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcAuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(AuthRegister authRegister) {
        String sql = "insert into `auths` (user_id,email, password,salt) values (?,?, ?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, authRegister.getUserId());
            ps.setString(2, authRegister.getEmail());
            ps.setString(3, authRegister.getPassword());
            ps.setString(4, authRegister.getSalt());
            return ps;
        }, keyHolder);

        authRegister.setId(keyHolder.getKey().intValue());
    }

    @Override
    public Auth findByEmail(String email) {
        String sql = "SELECT a.*, p.role FROM `auths` a JOIN `user_permission` p ON a.user_id = p.user_id WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Auth.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
