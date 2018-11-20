package com.tarbadev.witchcraft;

import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase;
import com.tarbadev.witchcraft.converter.IngredientConverter;
import com.tarbadev.witchcraft.converter.UnitConverter;
import com.tarbadev.witchcraft.recipes.domain.usecase.*;
import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase;
import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
  @Bean
  public SaveRecipeUseCase saveRecipeUseCase() {
    return Mockito.mock(SaveRecipeUseCase.class);
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
  public SaveWeekUseCase saveWeekUseCase() {
    return Mockito.mock(SaveWeekUseCase.class);
  }

  @Bean
  public WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase() {
    return Mockito.mock(WeekFromYearAndWeekNumberUseCase.class);
  }

  @Bean
  public GetFavoriteRecipesUseCase getFavoriteRecipesUseCase() {
    return Mockito.mock(GetFavoriteRecipesUseCase.class);
  }

  @Bean
  public LastAddedRecipesUseCase lastAddedRecipesUseCase() {
    return Mockito.mock(LastAddedRecipesUseCase.class);
  }

  @Bean
  public DoesRecipeExistUseCase doesRecipeExistUseCase() {
    return Mockito.mock(DoesRecipeExistUseCase.class);
  }

  @Bean
  public SetFavoriteRecipeUseCase setFavoriteRecipeUseCase() {
    return Mockito.mock(SetFavoriteRecipeUseCase.class);
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
