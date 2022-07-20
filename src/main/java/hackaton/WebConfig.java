package hackaton;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hackaton.middleware.AuthorHandler;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorHandler());
    }
    
    
}
