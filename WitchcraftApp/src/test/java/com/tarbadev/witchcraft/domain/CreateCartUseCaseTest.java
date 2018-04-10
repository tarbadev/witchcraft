package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.domain.*;
import com.tarbadev.witchcraft.domain.converter.IngredientConverter;
import com.tarbadev.witchcraft.persistence.DatabaseCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateCartUseCaseTest {
  @Autowired private IngredientConverter ingredientConverter;
  @Mock
  DatabaseCartRepository databaseCartRepository;

  private CreateCartUseCase subject;

  @Before
  public void setUp() {
    subject = new CreateCartUseCase(databaseCartRepository, ingredientConverter);
  }

  @Test
  public void execute_savesCartWithItems() {
    List<Recipe> recipes = Collections.singletonList(
        Recipe.builder()
            .ingredients(Arrays.asList(
                Ingredient.builder()
                    .name("Ingredient 1")
                    .quantity(2.2)
                    .unit("lb")
                    .build(),
                Ingredient.builder()
                    .name("Ingredient 2")
                    .quantity(10.0)
                    .unit("oz")
                    .build()
            ))
            .build()
    );
    List<Item> items = recipes.stream()
        .flatMap(recipe -> recipe.getIngredients().stream()
            .map(ingredient -> Item.builder()
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .unit(ingredient.getUnit())
                .build()
            )
        )
        .sorted(Comparator.comparing(Item::getName))
        .collect(Collectors.toList());

    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(items)
        .build();

    given(databaseCartRepository.save(cart)).willReturn(cart);

    assertThat(subject.execute(recipes)).isEqualTo(cart);

    verify(databaseCartRepository).save(cart);
  }

  @Test
  public void execute_addsItemsWithSameNameAndConvertUnit() {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder()
            .ingredients(Arrays.asList(
                Ingredient.builder()
                    .name("Ingredient 1")
                    .quantity(1.2)
                    .unit("lb")
                    .build(),
                Ingredient.builder()
                    .name("Ingredient 2")
                    .quantity(10.0)
                    .unit("oz")
                    .build()
            ))
            .build(),
        Recipe.builder()
            .ingredients(Arrays.asList(
                Ingredient.builder()
                    .name("Ingredient 1")
                    .quantity(2.2)
                    .unit("lb")
                    .build(),
                Ingredient.builder()
                    .name("Ingredient 2")
                    .quantity(10.0)
                    .unit("ml")
                    .build()
            ))
            .build()
    );
    List<Item> items = Arrays.asList(
        Item.builder()
            .name("Ingredient 1")
            .quantity(2.2 + 1.2)
            .unit("lb")
            .build(),
        Item.builder()
            .name("Ingredient 2")
            .quantity(10.33814)
            .unit("oz")
            .build()
    );
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(items)
        .build();

    given(ingredientConverter.addToHighestUnit(recipes.get(0).getIngredients().get(1), recipes.get(1).getIngredients().get(1)))
        .willReturn(Ingredient.builder().name("Ingredient 2").quantity(10.33814).unit("oz").build());
    given(databaseCartRepository.save(cart)).willReturn(cart);

    assertThat(subject.execute(recipes)).isEqualTo(cart);

    verify(databaseCartRepository).save(cart);
  }
}