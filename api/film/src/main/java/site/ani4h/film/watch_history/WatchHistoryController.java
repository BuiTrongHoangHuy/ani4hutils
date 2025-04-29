package site.ani4h.film.watch_history;

import org.springframework.web.bind.annotation.*;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;

@RestController
@RequestMapping("v1/watch-history")
public class WatchHistoryController {
    private final WatchHistoryService watchHistoryService;

    public WatchHistoryController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }

    @GetMapping("/is-watched")
    public boolean isWatched(@ModelAttribute WatchHistoryRequest request) {
        return watchHistoryService.isWatched(request);
    }

    @PostMapping("/upsert")
    public void upsert(@RequestBody WatchHistoryRequest request) {
        watchHistoryService.upsert(request);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody WatchHistoryRequest request) {
        watchHistoryService.delete(request);
    }
}
