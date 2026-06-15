package com.pancaran.master.config;

import java.util.List;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@SecurityScheme(
    name = "X-JWT",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER,
    paramName = "X-JWT",
    description = "Input JWT token here"
)
public class OpenApiConfig {

    /* 
     * Starter OpenApi Configuration
     * Add GroupedOpenApi beans here as you create new features
     */

    public OpenApiCustomizer apiPrefixRemoverCustomiser(String basePath, List<String> tagsOrder) {
        return openApi -> {
            Paths paths = openApi.getPaths();
            Paths newPaths = new Paths();
            for (String key : paths.keySet()) {
                PathItem pathItem = paths.get(key);
                newPaths.put(key.replace(basePath, ""), pathItem);
            }
            if (!openApi.getServers().isEmpty()) {
                final Server server = openApi.getServers().get(0);
                server.setUrl(server.getUrl() + basePath);
            }
            openApi.setPaths(newPaths);
            if (openApi.getTags() != null) {
                openApi.setTags(openApi.getTags().stream()
                    .sorted((tag1, tag2) -> {
                        int index1 = tagsOrder.indexOf(tag1.getName());
                        int index2 = tagsOrder.indexOf(tag2.getName());
                        return Integer.compare(index1, index2);
                    }).collect(Collectors.toList())
                );
            }
        };
    }
}
