package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateCartUseCaseTest {
  @Autowired
  TestResources testResources;
  @Mock
  DatabaseCartRepository databaseCartRepository;

  private CreateCartUseCase subject;

  @Before
  public void setUp() {
    subject = new CreateCartUseCase(databaseCartRepository);
  }

  @Test
  public void execute_savesCartWithItems() {
    List<Recipe> recipes = Arrays.asList(
        testResources.getRecipe(),
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
  public void execute_addsItemsWithSameNameAndUnit() {
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
            .quantity(10.0)
            .unit("oz")
            .build(),
        Item.builder()
            .name("Ingredient 2")
            .quantity(10.0)
            .unit("ml")
            .build()
    );
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(items)
        .build();

    given(databaseCartRepository.save(cart)).willReturn(cart);

    assertThat(subject.execute(recipes)).isEqualTo(cart);

    verify(databaseCartRepository).save(cart);
  }
}