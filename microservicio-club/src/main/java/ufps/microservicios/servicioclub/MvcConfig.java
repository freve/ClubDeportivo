package ufps.microservicios.servicioclub;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
//        super.addResourceHandlers(registry);

        String resourcePath = Paths.get("archivos").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/archivos/**")
        .addResourceLocations(resourcePath);
    }

}