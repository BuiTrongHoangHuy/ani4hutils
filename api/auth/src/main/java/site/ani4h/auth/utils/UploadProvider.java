package site.ani4h.auth.utils;


import site.ani4h.shared.common.Image;

public interface UploadProvider {
    Image uploadImage(byte[] data, String dst );
    String uploadFileWithPreSignedUrl(String dst);
}
