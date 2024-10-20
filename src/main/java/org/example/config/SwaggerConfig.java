package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Custom Open API.
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .servers(List.of(new Server().url("http://localhost:8888/")))
            .info(new Info().title("Gym Application")
                .description("Gym Application")
                .version("1.0"));
    }
}
