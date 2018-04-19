package com.tarbadev.witchcraft;

import com.tarbadev.witchcraft.domain.*;
import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import com.tarbadev.witchcraft.domain.converter.UnitConverter;
import com.tarbadev.witchcraft.persistence.DatabaseCartRepository;
import com.tarbadev.witchcraft.persistence.DatabaseWeekRepository;
import com.tarbadev.witchcraft.web.GetRecipeDetailsFromFormUseCase;
import org.mockito.Mock;
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
  public GetRecipeDetailsFromUrlUseCase getRecipeDetailsFromUrlUseCase() {
    return Mockito.mock(GetRecipeDetailsFromUrlUseCase.class);
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
  public GetRecipeDetailsFromFormUseCase getRecipeDetailsFromFormUseCase() {
    return Mockito.mock(GetRecipeDetailsFromFormUseCase.class);
  }

  @Bean
  public ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase() {
    return Mockito.mock(ConvertAndAddSameIngredientUseCase.class);
  }

  @Bean
  public DeleteRecipeUseCase deleteRecipeUseCase() {
    return Mockito.mock(DeleteRecipeUseCase.class);
  }

  @Bean
  public RateRecipeUseCase rateRecipeUseCase() {
    return Mockito.mock(RateRecipeUseCase.class);
  }

  @Bean
  public UnitConverter unitConverter() {
    return Mockito.mock(UnitConverter.class);
  }

  @Bean
  public IngredientConverter ingredientConverter() {
    return Mockito.mock(IngredientConverter.class);
  }

  @Bean
  public TestResources testResources() {
    return new TestResources();
  }
}
