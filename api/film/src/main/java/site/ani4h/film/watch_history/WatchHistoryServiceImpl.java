package site.ani4h.film.watch_history;

import org.springframework.stereotype.Service;
import site.ani4h.film.watch_history.entity.EpisodeWatchHistory;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

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

    @Override
    public List<EpisodeWatchHistory> getWatchHistoryByUserId(WatchHistoryRequest request, Paging paging) {
        return watchHistoryRepository.getWatchHistoryByUserId(request, paging);
    }

    @Override
    public List<Integer> getRecentByUserId(int userId, int limit) {
        return watchHistoryRepository.getRecentByUserId(userId, limit);
    }
}
