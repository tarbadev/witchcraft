package com.tarbadev.witchcraft;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    public AddRecipeUseCase addRecipeUseCase() {
        return Mockito.mock(AddRecipeUseCase.class);
    }

    @Bean
    public DatabaseRecipeRepository databaseRecipeRepository() {
        return Mockito.mock(DatabaseRecipeRepository.class);
    }

    @Bean TestResources testResources() {
        return new TestResources();
    }
}
