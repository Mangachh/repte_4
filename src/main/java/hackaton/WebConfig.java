package hackaton;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hackaton.middleware.AuthorHandler;
import hackaton.middleware.BookHandler;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorHandler());
        registry.addInterceptor(new BookHandler());
    }
    
    
}
