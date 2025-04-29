package site.ani4h.film.watch_history;

import org.springframework.stereotype.Repository;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;

@Repository
public interface WatchHistoryRepository {
    boolean isWatched(WatchHistoryRequest request);

    void add(WatchHistoryRequest request);

    void update(WatchHistoryRequest request);

    void delete(WatchHistoryRequest request);
}
