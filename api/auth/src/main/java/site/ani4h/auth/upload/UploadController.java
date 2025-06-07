package site.ani4h.auth.upload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.ani4h.shared.common.ApiResponse;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("/v1/upload")
public class  UploadController {

    private final UploadService uploadService;
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) {
        try {
            Image uploadedImage = this.uploadService.uploadImage(file.getBytes(), folder, file.getOriginalFilename());
            return ResponseEntity.ok(ApiResponse.success(uploadedImage));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("film")
    public ResponseEntity<?> uploadFilm() {
        try {
            String url = this.uploadService.UploadFilm("film","hehe.webm");
            return ResponseEntity.ok(ApiResponse.success(url));
        } catch (Exception e ) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("episode-video-url")
    public ResponseEntity<?> getEpisodeVideoUploadUrl(
            @RequestParam("filmId") Uid filmId,
            @RequestParam("episodeNumber") int episodeNumber,
            @RequestParam("fileExtension") String fileExtension) {
        try {
            String folder = "film/" + filmId.getLocalId()+"/episode_"+episodeNumber+"/";
            String fileName = "episode_" + episodeNumber + "." + fileExtension;
            String url = this.uploadService.UploadFilm(folder, fileName);
            return ResponseEntity.ok(ApiResponse.success(url));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
