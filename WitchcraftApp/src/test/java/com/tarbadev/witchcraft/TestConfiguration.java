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
  public GetRecipeUseCase getRecipeUseCase() {
    return Mockito.mock(GetRecipeUseCase.class);
  }

  @Bean
  public CartCatalogUseCase cartCatalogUseCase() {
    return Mockito.mock(CartCatalogUseCase.class);
  }

  @Bean
  public CreateCartUseCase createCartUseCase() {
    return Mockito.mock(CreateCartUseCase.class);
  }

  @Bean
  public GetCartUseCase getCartUseCase() {
    return Mockito.mock(GetCartUseCase.class);
  }

  @Bean
  public GetCurrentWeekUseCase getCurrentWeekUseCase() {
    return Mockito.mock(GetCurrentWeekUseCase.class);
  }

  @Bean
  public DatabaseRecipeRepository databaseRecipeRepository() {
    return Mockito.mock(DatabaseRecipeRepository.class);
  }

  @Bean
  public DatabaseCartRepository databaseCartRepository() {
    return Mockito.mock(DatabaseCartRepository.class);
  }

  @Bean
  public DatabaseWeekRepository databaseWeekRepository() {
    return Mockito.mock(DatabaseWeekRepository.class);
  }

  @Bean
  public UnitConverter unitConverter() {
    return Mockito.mock(UnitConverter.class);
  }

  @Bean IngredientConverter ingredientConverter() {
    return Mockito.mock(IngredientConverter.class);
  }

  @Bean
  public TestResources testResources() {
    return new TestResources();
  }
}
