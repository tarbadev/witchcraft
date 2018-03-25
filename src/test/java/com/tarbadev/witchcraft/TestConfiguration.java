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
    public RecipeCatalogUseCase recipeCatalogUseCase() {
        return Mockito.mock(RecipeCatalogUseCase.class);
    }

    @Bean
    public GetRecipeDetailsUseCase getRecipeDetailsUseCase() {
        return Mockito.mock(GetRecipeDetailsUseCase.class);
    }

    @Bean
    public DatabaseRecipeRepository databaseRecipeRepository() {
        return Mockito.mock(DatabaseRecipeRepository.class);
    }

    @Bean TestResources testResources() {
        return new TestResources();
    }
}
