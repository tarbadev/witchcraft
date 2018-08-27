package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.entity.Item;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class DatabaseCartRepositoryTest {
  @Autowired private TestEntityManager entityManager;
  @Autowired private CartEntityRepository cartEntityRepository;

  private DatabaseCartRepository subject;

  @Before
  public void setUp() {
    subject = new DatabaseCartRepository(cartEntityRepository);
  }

  @Test
  public void findAll() {
    entityManager.persist(CartEntity.builder().build());
    entityManager.persist(CartEntity.builder().build());
    entityManager.persist(CartEntity.builder().build());

    entityManager.flush();
    entityManager.clear();

    assertThat(subject.findAll().size()).isEqualTo(3);
  }

  @Test
  public void save() {
    List<Recipe> recipes = Collections.singletonList(
        EntityToDomain.recipeMapper(
            entityManager.persistAndFlush(
                RecipeEntity.builder()
                    .name("Lasagna")
                    .ingredients(Arrays.asList(
                        IngredientEntity.builder()
                            .name("Ingredient 3")
                            .unit("cup")
                            .quantity(2.0)
                            .build(),
                        IngredientEntity.builder()
                            .name("Ingredient 1")
                            .unit("lb")
                            .quantity(2.0)
                            .build(),
                        IngredientEntity.builder()
                            .name("Ingredient 2")
                            .unit("oz")
                            .quantity(8.0)
                            .build()
                    ))
                    .steps(Collections.emptyList())
                    .build()
            )
        )
    );

    entityManager.clear();

    List<Item> items = Arrays.asList(
        Item.builder()
            .name("Ingredient 1")
            .unit("lb")
            .quantity(2.0)
            .build(),
        Item.builder()
            .name("Ingredient 2")
            .unit("oz")
            .quantity(8.0)
            .build(),
        Item.builder()
            .name("Ingredient 3")
            .unit("cup")
            .quantity(2.0)
            .build()
    );
    Cart cart = subject.save(Cart.builder()
        .recipes(recipes)
        .items(items)
        .build());

    entityManager.clear();

    CartEntity savedCart = entityManager.find(CartEntity.class, cart.getId());

    assertThat(savedCart.getItems().size()).isEqualTo(3);
    assertThat(savedCart.getRecipes().size()).isEqualTo(1);
  }

  @Test
  public void save_createsDateForCart() {
    CartEntity cart = CartEntity.builder().build();
    assertThat(cart.getCreatedAt()).isNull();

    entityManager.persistAndFlush(cart);

    assertThat(cart.getCreatedAt()).isNotNull();
  }

  @Test
  public void findById() {
    CartEntity cart = entityManager.persistFlushFind(
        CartEntity.builder()
            .build()
    );

    entityManager.clear();

    assertThat(subject.findById(cart.getId())).isEqualToComparingFieldByFieldRecursively(cart);
  }

  @Test
  public void findById_returnsItemsOrderedAlphabetically() {
    CartEntity cart = entityManager.persistAndFlush(
        CartEntity.builder()
            .items(
                Arrays.asList(
                    ItemEntity.builder()
                        .name("Parsley")
                        .build(),
                    ItemEntity.builder()
                        .name("Tomatoes")
                        .build(),
                    ItemEntity.builder()
                        .name("Beef")
                        .build()
                )
            )
            .build()
    );

    entityManager.clear();

    List<Item> items = cart.getItems().stream()
        .map(itemEntity -> Item.builder()
            .id(itemEntity.getId())
            .name(itemEntity.getName())
            .quantity(itemEntity.getQuantity())
            .unit(itemEntity.getUnit())
            .build())
        .sorted(Comparator.comparing(Item::getName))
        .collect(Collectors.toList());

    List<Item> savedItems = subject.findById(cart.getId()).getItems();
    assertThat(savedItems).isEqualTo(items);
    assertThat(savedItems.get(0)).isEqualTo(items.get(0));
    assertThat(savedItems.get(1)).isEqualTo(items.get(1));
    assertThat(savedItems.get(2)).isEqualTo(items.get(2));
  }
}