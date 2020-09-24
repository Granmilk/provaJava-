package br.com.tokiomarine.config;

import br.com.tokiomarine.interceptor.WebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SecurityConfig implements WebMvcConfigurer {


    private final WebInterceptor webInterceptor;

    public SecurityConfig(WebInterceptor webInterceptor) {
        this.webInterceptor = webInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor);
    }


}
