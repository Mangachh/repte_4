package cbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cbs.middleware.AuthorHandler;
import cbs.middleware.BookHandler;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorHandler());
        registry.addInterceptor(new BookHandler());
    }
    
    
}
