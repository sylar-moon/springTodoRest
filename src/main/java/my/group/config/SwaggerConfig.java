package my.group.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


//    private String version = "2.0";
//    @Value("${project.version}")
//    private String version;

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        String version = buildProperties.getVersion();

        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();

        if (version != null) {
            info.version(version);
        }

        info.title("API Title");
        info.description("API Description");
        info.license(new License().name("Apache 2.0").url("http://springdoc.org"));

        openAPI.components(new Components()).info(info);

        return openAPI;
    }
}