package site.ani4h.film.film;

import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;
import java.util.List;

public class Film {
    private Uid id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String synopsis;
    private List<Image> images;
    private Float avgStar;
    private int totalStar;
    private String state;                    //
    private int maxEpisodes;             // int (nullable)
    private int numEpisodes;             // int (nullable)
    private int year;                    // SMALLINT UNSIGNED (0-65535)
    private String season;                   // enum ('spring','summer','fall','winter') DEFAULT NULL
    private Float averageEpisodeDuration;    // float (nullable)
    private Uid ageRatingId;
    private int status;                  // int DEFAULT 1
    private LocalDateTime createdAt;         // datetime DEFAULT CURRENT_TIMESTAMP
    private LocalDateTime updatedAt;         // datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
}
