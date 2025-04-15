package site.ani4h.auth.auth;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.auth.auth.entity.ExternalAuth;
import site.ani4h.auth.auth.entity.ExternalAuthRegister;
import site.ani4h.auth.user.Role;

public class JdbcExternalAuthRepository implements ExternalAuthRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcExternalAuthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void create(ExternalAuthRegister data) {
        String sql = """
               INSERT INTO external_auths (user_id, external_user_id, external_auth_provider_id, auth_token) VALUE (?,?,?,?);
               """;

        String insertPermissionSql = "INSERT INTO `user_permission` (user_id, role) VALUES (?, ?)";
        jdbcTemplate.update(sql,data.getUserId(),data.getExternalUserId(),data.getProviderId(),data.getToken());
        jdbcTemplate.update(insertPermissionSql,data.getUserId(), Role.USER.toString());
    }

    @Override
    public ExternalAuth find(int provideId, String externalId) {
        String sql = """
                SELECT * FROM external_auths WHERE external_user_id = ? AND external_auth_provider_id = ?
                """;
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ExternalAuth.class),provideId,externalId);
    }
}
