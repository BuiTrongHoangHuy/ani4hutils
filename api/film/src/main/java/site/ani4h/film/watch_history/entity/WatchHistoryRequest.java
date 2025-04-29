package site.ani4h.film.watch_history.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class WatchHistoryRequest {
    private Uid userId;
    private Uid episodeId;
    private int watchedDuration;
}
