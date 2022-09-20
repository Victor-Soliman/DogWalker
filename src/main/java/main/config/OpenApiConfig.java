package main.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;




@Configuration
@OpenAPIDefinition(info = @Info(title = "Custom REST Documentation title",
        description = "All REST API custom description", version = "1"))

public class OpenApiConfig {



}
