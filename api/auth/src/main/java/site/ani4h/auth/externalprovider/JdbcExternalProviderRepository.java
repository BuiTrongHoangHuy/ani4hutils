package site.ani4h.auth.externalprovider;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcExternalProviderRepository implements ExternalProviderRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcExternalProviderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(ExternalAuthProviderCreate create) {
        String sql = """
                INSERT INTO  external_auth_providers (name, endpoint) VALUE (?,?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,create.getName());
            ps.setString(2, create.getEndPoint());
            return ps;
        },keyHolder);
        create.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }
    @Override
    public void update(int id, ExternalAuthProviderUpdate update) {
        String sql = """
                UPDATE external_auth_providers
                SET name=COALESCE(?,name), endpoint=COALESCE(?,endpoint)
                WHERE id=?;
                """;
        jdbcTemplate.update(sql,update.getName(),update.getEndPoint(),id);
    }
    @Override
    public List<ExternalAuthProvider> listAll() {
        String sql = """
                SELECT * FROM external_auth_providers;
                """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(ExternalAuthProvider.class)
        );
    }

    @Override
    public void delete(int id) {
        String sql = """
                UPDATE external_auth_providers SET status = ? WHERE id = ?;
                """;

        jdbcTemplate.update(sql,0,id);
    }

    @Override
    public ExternalAuthProvider get(int id) {
        String sql = """
                SELECT * FROM external_auth_providers WHERE id=?;
                """;
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ExternalAuthProvider.class),id);
    }
}
