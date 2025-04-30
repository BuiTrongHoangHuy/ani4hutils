package site.ani4h.film.watch_history;

import org.springframework.stereotype.Service;
import site.ani4h.film.watch_history.entity.EpisodeWatchHistory;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;
import site.ani4h.shared.common.Paging;

import java.util.List;

@Service
public interface WatchHistoryService {
    boolean isWatched(WatchHistoryRequest request);

    void upsert(WatchHistoryRequest request);

    void delete(WatchHistoryRequest request);

    List<EpisodeWatchHistory> getWatchHistoryByUserId(WatchHistoryRequest request, Paging paging);
}
