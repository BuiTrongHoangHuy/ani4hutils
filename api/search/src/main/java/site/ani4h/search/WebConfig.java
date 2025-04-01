package site.ani4h.search;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.ani4h.shared.middlewares.RequestExtractor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestExtractor())
                .addPathPatterns("/**");
    }
}
