package com.tarbadev.witchcraft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@EnableWebMvc
public class WitchcraftConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String workingDirectory = System.getProperty("user.dir");

        if (workingDirectory.endsWith("/WitchcraftApp")) {
            workingDirectory = workingDirectory.substring(0, workingDirectory.lastIndexOf("/backend"));
        }

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/",
                "classpath:/",
                "file:///" + workingDirectory + "/WitchcraftUi/build/dist/"
        );
    }
}
