package com.example.abricateengineering.Config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
// @Configuration
// @EnableWebSocketMessageBroker
// public class CorsConfig implements WebSocketMessageBrokerConfigurer{
//     @Override
//     public void configureMessageBroker(@SuppressWarnings("null") MessageBrokerRegistry registry) {
//         registry.enableSimpleBroker("/topic");
//         registry.setApplicationDestinationPrefixes("/app");
//     }

//     @Override
//     public void registerStompEndpoints(@SuppressWarnings("null") StompEndpointRegistry registry) {
//         registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//     }

// @Bean
// public WebMvcConfigurer corsConfigurer() {
//     return new WebMvcConfigurer() {
//         @Override
//         public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
//             registry.addMapping("/**")
//                     .allowedOrigins("http://localhost:3000")
//                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                     .allowedHeaders("*")
//                     .allowCredentials(true);
//         }
//     };
// }
// }
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}