package com.tarbadev.witchcraft.carts.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarbadev.witchcraft.carts.domain.usecase.CartCatalogUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.carts.domain.usecase.GetCartUseCase;
import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.carts.rest.CreateCartRequest;
import com.tarbadev.witchcraft.recipes.domain.usecase.RecipeCatalogUseCase;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartsRestControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private RecipeCatalogUseCase recipeCatalogUseCase;
  @Autowired
  private CreateCartUseCase createCartUseCase;
  @Autowired
  private CartCatalogUseCase cartCatalogUseCase;
  @Autowired
  private GetCartUseCase getCartUseCase;

  @Test
  public void getCart() throws Exception {
    Cart cart = Cart.builder().id(12).build();

    given(getCartUseCase.execute(cart.getId())).willReturn(cart);

    mvc.perform(get("/api/carts/" + cart.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(cart)));
  }

  @Test
  public void getAll() throws Exception {
    List<Cart> carts = asList(
        Cart.builder().build(),
        Cart.builder().build(),
        Cart.builder().build()
    );

    given(cartCatalogUseCase.execute()).willReturn(carts);

    mvc.perform(get("/api/carts"))
    .andExpect(status().isOk())
    .andExpect(content().json(new ObjectMapper().writeValueAsString(carts)));
  }

  @Test
  public void create() throws Exception {
    List<CreateCartRequest> createCartRequests = asList(
        CreateCartRequest.builder().id(1).build(),
        CreateCartRequest.builder().id(2).build(),
        CreateCartRequest.builder().id(4).build()
    );

    List<Recipe> recipes = asList(
        Recipe.builder().id(1).build(),
        Recipe.builder().id(2).build(),
        Recipe.builder().id(3).build(),
        Recipe.builder().id(4).build(),
        Recipe.builder().id(5).build()
    );
    List<Recipe> cartRecipes = recipes.stream()
        .filter(recipe -> createCartRequests.stream()
            .anyMatch(createCartRequest -> createCartRequest.getId().equals(recipe.getId()))
        )
        .collect(Collectors.toList());
    Cart cart = Cart.builder()
        .recipes(recipes).build();

    given(recipeCatalogUseCase.execute()).willReturn(recipes);
    given(createCartUseCase.execute(cartRecipes)).willReturn(cart);

    mvc.perform(post("/api/carts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(createCartRequests))
    )
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(cart)));
  }
}