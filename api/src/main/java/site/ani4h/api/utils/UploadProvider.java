package site.ani4h.api.utils;

import site.ani4h.api.common.Image;

public interface UploadProvider {
    Image uploadImage(byte[] data, String dst );
}
