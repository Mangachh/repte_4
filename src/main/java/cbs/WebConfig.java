package cbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cbs.middleware.AuthorHandler;
import cbs.middleware.BookHandler;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    AuthorHandler authHandler;

    @Autowired
    BookHandler bookHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authHandler);
        registry.addInterceptor(this.bookHandler);
    }
    
    
}
