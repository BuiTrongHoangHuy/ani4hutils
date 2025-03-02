package site.ani4h.api.upload;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.ani4h.api.common.Image;
import site.ani4h.api.utils.UploadProvider;

import java.io.File;
import java.util.UUID;

@Service
public class UploadService {
    private final UploadProvider provider;
    @Autowired
    public UploadService(UploadProvider provider) {
        this.provider = provider;
    }

    public Image uploadImage(byte[] data, String folder, String fileName) {
        String ext  = FilenameUtils.getExtension(fileName);
        UUID uuid = UUID.randomUUID();
        String dst = folder + File.separator + uuid + "." + ext;
        return this.provider.uploadImage(data, dst);
    }

    public String UploadFilm(String folder, String fileName) {
        String ext  = FilenameUtils.getExtension(fileName);
        UUID uuid = UUID.randomUUID();
        String dst = folder + File.separator + uuid + "." + ext;
        return this.provider.uploadFileWithPreSignedUrl(dst);
    }
}
