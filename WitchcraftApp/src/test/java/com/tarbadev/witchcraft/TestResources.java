package com.tarbadev.witchcraft;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "test-resources")
@Getter
@Setter
public class TestResources {
    private Recipe recipe;
}
