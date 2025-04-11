package site.ani4h.auth.externalprovider;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExternalProviderRepository {
    void create(ExternalAuthProviderCreate create);
    void update(int id, ExternalAuthProviderUpdate update);
    List<ExternalAuthProvider> listAll();
    void delete(int id);
    ExternalAuthProvider get(int id);
}
