package site.ani4h.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.ani4h.api.utils.S3UploadProvider;
import site.ani4h.api.utils.UploadProvider;

@Configuration
public class BeanConfig {
    @Bean
    public UploadProvider s3UploadProvider() {
        return new S3UploadProvider();
    }
}
