package site.ani4h.auth.externalprovider;

import org.springframework.stereotype.Service;
import site.ani4h.shared.common.Requester;

import java.util.List;


@Service
public interface ExternalProviderService {
    List<ExternalAuthProvider> listAll();
    ExternalAuthProvider get(int id);
    void create(Requester requester, ExternalAuthProviderCreate create);
    void update(Requester requester,int id, ExternalAuthProviderUpdate update);
    void delete(Requester requester,int id);
}
