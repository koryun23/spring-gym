//package org.example.config;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.servers.Server;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//@Slf4j
//@Configuration
//@Import({org.springdoc.webmvc.ui.SwaggerConfig.class})
//public class CustomSwaggerConfig {
//
//    /**
//     * Custom Open API.
//     */
//    @Bean
//    public OpenAPI customOpenApi() {
//        return new OpenAPI()
//            .servers(List.of(new Server().url("http://localhost:8888/")))
//            .info(new Info().title("Gym Application")
//                .description("Gym Application")
//                .version("1.0"));
//    }
//}
