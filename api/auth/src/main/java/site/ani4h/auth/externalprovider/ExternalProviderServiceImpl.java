package site.ani4h.auth.externalprovider;

import org.springframework.stereotype.Component;
import site.ani4h.shared.common.Requester;

import java.util.List;

@Component
public class ExternalProviderServiceImpl implements ExternalProviderService {
    private final ExternalProviderRepository externalProviderRepository;

    public ExternalProviderServiceImpl(ExternalProviderRepository externalProviderRepository) {
        this.externalProviderRepository = externalProviderRepository;
    }

    @Override
    public void create(Requester requester, ExternalAuthProviderCreate create) {
        externalProviderRepository.create(create);
    }

    @Override
    public void update(Requester requester, int id, ExternalAuthProviderUpdate update) {
        externalProviderRepository.update(id, update);
    }

    @Override
    public void delete(Requester requester, int id) {
        externalProviderRepository.delete(id);
    }

    @Override
    public List<ExternalAuthProvider> listAll() {
        return externalProviderRepository.listAll();
    }

    @Override
    public ExternalAuthProvider get(int id) {
        return externalProviderRepository.get(id);
    }
}
