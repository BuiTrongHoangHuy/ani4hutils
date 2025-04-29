package site.ani4h.film.watch_history;

import org.springframework.stereotype.Service;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;

@Service
public interface WatchHistoryService {
    boolean isWatched(WatchHistoryRequest request);

    void upsert(WatchHistoryRequest request);

    void delete(WatchHistoryRequest request);
}
