package site.ani4h.auth.subscription;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import site.ani4h.auth.subscription.entity.Subscription;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcSubscriptionRepository implements SubscriptionRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcSubscriptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createSubscription(SubscriptionRequest subscription) {
        String sql = "INSERT INTO subscriptions (name, price, quality, resolution, max_simultaneous_streams) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subscription.getName());
            ps.setFloat(2, subscription.getPrice());
            ps.setString(3, subscription.getQuality());
            ps.setString(4, subscription.getResolution());
            ps.setInt(5, subscription.getMaxSimultaneousStreams());
            return ps;
        }, keyHolder);

        int subcriptionId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        String sql2 = "INSERT INTO user_subscriptions (user_id, subscription_id) VALUES (?, ?)";
        jdbcTemplate.update(sql2, subscription.getUserId().getLocalId(),subcriptionId);
    }

    @Override
    public Subscription getSubscriptionById(int id) {
        String sql = "SELECT * FROM subscriptions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subscription.class));
    }

    @Override
    public void updateSubscription(SubscriptionRequest subscription) {
        StringBuilder sql = new StringBuilder("UPDATE subscriptions SET ");
        List<Object> params = new java.util.ArrayList<>();

        if (subscription.getName() != null) {
            sql.append("name = ?, ");
            params.add(subscription.getName());
        }
        if (subscription.getPrice() > 0) {
            sql.append("price = ?, ");
            params.add(subscription.getPrice());
        }
        if (subscription.getQuality() != null) {
            sql.append("quality = ?, ");
            params.add(subscription.getQuality());
        }
        if (subscription.getResolution() != null) {
            sql.append("resolution = ?, ");
            params.add(subscription.getResolution());
        }
        if (subscription.getMaxSimultaneousStreams() > 0) {
            sql.append("max_simultaneous_streams = ?, ");
            params.add(subscription.getMaxSimultaneousStreams());
        }

        if(params.isEmpty()){
            throw new IllegalArgumentException("No fields to update");
        }

        sql.setLength(sql.length() - 2); // Remove the last comma and space
        sql.append(" WHERE id = ?");
        params.add(subscription.getId().getLocalId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void deleteSubscription(int id) {
        String sql = "DELETE FROM subscriptions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Subscription> getSubscriptions() {
        String sql = "SELECT * FROM subscriptions";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subscription.class));
    }

    @Override
    public Subscription getUserSubscription(int id) {
        String sql = """
            select s.* from subscriptions s
            join user_subscriptions us on us.subscription_id = s.id
            where us.user_id = ?;
            """;
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Subscription.class));
    }
}
