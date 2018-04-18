package com.tarbadev.witchcraft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class WitchcraftConfig implements WebMvcConfigurer {
    private static final String BACKEND = "/WitchcraftApp";
    private static final String FRONTEND = "/WitchcraftUi";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workingDirectory = System.getProperty("user.dir");

        if (workingDirectory.endsWith(BACKEND)) {
            workingDirectory = workingDirectory.substring(0, workingDirectory.lastIndexOf(BACKEND));
        }

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/",
                "classpath:/",
                "file:///" + workingDirectory + FRONTEND + "/build/dist/"
        );
    }
}
