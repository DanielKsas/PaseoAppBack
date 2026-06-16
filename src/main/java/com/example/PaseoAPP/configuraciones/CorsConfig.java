package com.example.PaseoAPP.configuraciones;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// CUalquier agente externo a mi api pueda consumirla 
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas (/paseoapi/v1/...)
                .allowedOrigins("*") // Permite peticiones desde cualquier origen (Frontend)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                .allowedHeaders("*"); // Permite cualquier cabecera
    }
}