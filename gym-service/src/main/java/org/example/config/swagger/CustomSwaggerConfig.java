package org.example.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomSwaggerConfig {

    /**
     * Custom Open API Configuration Bean.
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
            .servers(List.of(
                    new Server().url("http://localhost:8888/")
                )
            )
            .info(
                new Info().title("Gym Api.")
                    .description("Documentation for Gym Api.")
                    .version("1.0.0")
            );
    }


}