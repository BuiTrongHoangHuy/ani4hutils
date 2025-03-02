package site.ani4h.api.upload;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.ani4h.api.common.ApiResponse;
import site.ani4h.api.common.Image;

@RestController
@RequestMapping("upload")
public class UploadController {

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
}
