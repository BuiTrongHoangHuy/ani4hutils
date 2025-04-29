package site.ani4h.film.watch_history;

import org.springframework.stereotype.Service;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;

@Service
public class WatchHistoryServiceImpl implements WatchHistoryService{
    private final WatchHistoryRepository watchHistoryRepository;
    public WatchHistoryServiceImpl(WatchHistoryRepository watchHistoryRepository) {
        this.watchHistoryRepository = watchHistoryRepository;
    }

    @Override
    public boolean isWatched(WatchHistoryRequest request) {
        return watchHistoryRepository.isWatched(request);
    }

    @Override
    public void upsert(WatchHistoryRequest request) {
        if (watchHistoryRepository.isWatched(request)) {
            watchHistoryRepository.update(request);
        } else {
            watchHistoryRepository.add(request);
        }
    }

    @Override
    public void delete(WatchHistoryRequest request) {
        if(watchHistoryRepository.isWatched(request))
        {
            watchHistoryRepository.delete(request);
        }
    }
}
