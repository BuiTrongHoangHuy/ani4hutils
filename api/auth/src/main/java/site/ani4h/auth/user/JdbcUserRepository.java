package site.ani4h.auth.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.ani4h.share.common.Paging;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void create(UserCreate userCreate) {
        String sql = "insert into `users` (first_name, last_name, display_name, date_of_birth, role) VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userCreate.getFirstName());
            ps.setString(2, userCreate.getLastName());
            ps.setString(3, userCreate.getDisplayName());
            ps.setString(4, userCreate.getDateOfBirth().toString());
            ps.setString(5, userCreate.getRole().toString());
            return ps;
        }, keyHolder);
        userCreate.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }
    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM `users` WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                id
        );
    }

    @Override
    public List<User> getUsers(Paging paging) {
        String sql = "SELECT * FROM `users` LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(User.class),
                paging.getPageSize(),
                paging.getOffset()
        );
    }
}
