package site.ani4h.api.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.ani4h.api.common.Image;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Properties;


@Component
public final class S3UploadProvider implements UploadProvider {
    private  S3Client s3Client;
    @Value("${aws.s3.image-bucket}")
    private String bucket;
    @Value("${cdn.url}")
    private String cdnUrl;
    @Value("${aws.access-key-id}")
    private String accessKey;
    @Value("${aws.access-secret-key}")
    private String accessSecret;
    public S3UploadProvider() {
        // Constructor trống
    }

    @PostConstruct
    public void init() {
        this.s3Client = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.accessKey, this.accessSecret)))
                .build();
    }

    @Override
    public Image uploadImage(byte[] data, String dst) {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        String mimeType = null;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .key(dst)
                .bucket(this.bucket)
                .contentType(mimeType)
                .build();
        RequestBody requestBody = RequestBody.fromBytes(data);
        this.s3Client.putObject(putObjectRequest, requestBody);
        return new Image("", cdnUrl + "/" + dst, 0, 0, "", "s3");
    }
}