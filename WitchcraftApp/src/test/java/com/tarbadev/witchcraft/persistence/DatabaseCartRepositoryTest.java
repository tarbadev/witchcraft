package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Cart;
import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Item;
import com.tarbadev.witchcraft.domain.Recipe;
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
  public void findAll_ReturnsAllCarts() {
    entityManager.persist(Cart.builder().build());
    entityManager.persist(Cart.builder().build());
    entityManager.persist(Cart.builder().build());

    entityManager.flush();
    entityManager.clear();

    assertThat(subject.findAll().size()).isEqualTo(3);
  }

  @Test
  public void save() {
    List<RecipeEntity> recipes = Collections.singletonList(RecipeEntity.builder()
        .ingredients(Arrays.asList(
            Ingredient.builder()
                .name("Ingredient 3")
                .unit("cup")
                .quantity(2.0)
                .build(),
            Ingredient.builder()
                .name("Ingredient 1")
                .unit("lb")
                .quantity(2.0)
                .build(),
            Ingredient.builder()
                .name("Ingredient 2")
                .unit("oz")
                .quantity(8.0)
                .build()
        ))
        .build()
    );
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
    Cart cart = Cart.builder()
        .recipes(recipes)
        .items(items)
        .build();

    Cart savedCart = subject.save(cart);

    entityManager.clear();

    savedCart = entityManager.find(Cart.class, savedCart.getId());

    assertThat(savedCart.getItems().size()).isEqualTo(3);
    assertThat(savedCart.getItems().containsAll(items)).isTrue();
    assertThat(savedCart.getRecipes().size()).isEqualTo(1);
  }

  @Test
  public void save_createsDateForCart() {
    Cart cart = Cart.builder().build();
    assertThat(cart.getCreatedAt()).isNull();

    entityManager.persistAndFlush(cart);

    assertThat(cart.getCreatedAt()).isNotNull();
  }

  @Test
  public void findById() {
    Cart cart = entityManager.persistFlushFind(
        Cart.builder()
            .build()
    );

    entityManager.clear();

    assertThat(subject.findById(cart.getId())).isEqualToComparingFieldByFieldRecursively(cart);
  }

  @Test
  public void findById_returnsItemsOrderedAlphabetically() {
    Cart cart = entityManager.persistAndFlush(
        Cart.builder()
            .items(
                Arrays.asList(
                    Item.builder()
                        .name("Parsley")
                        .build(),
                    Item.builder()
                        .name("Tomatoes")
                        .build(),
                    Item.builder()
                        .name("Beef")
                        .build()
                )
            )
            .build()
    );

    entityManager.clear();

    List<Item> items = cart.getItems();
    items.sort(Comparator.comparing(Item::getName));

    List<Item> savedItems = subject.findById(cart.getId()).getItems();
    assertThat(savedItems.get(0)).isEqualTo(items.get(0));
    assertThat(savedItems.get(1)).isEqualTo(items.get(1));
    assertThat(savedItems.get(2)).isEqualTo(items.get(2));
  }
}