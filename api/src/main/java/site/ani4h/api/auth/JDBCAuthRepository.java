package site.ani4h.api.auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class JDBCAuthRepository implements AuthRepository {
    private final JdbcTemplate jdbcTemplate;
    public JDBCAuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(AuthRegister authRegister) {
        String sql = "insert into `auths` (email, password,salt) values (?, ?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, authRegister.getEmail());
            ps.setString(2, authRegister.getPassword());
            ps.setString(3, authRegister.getSalt());
            return ps;
        }, keyHolder);

        authRegister.setId(keyHolder.getKey().intValue());
    }
}
