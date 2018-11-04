package com.tarbadev.witchcraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.usecase.CreateCartUseCase;
import com.tarbadev.witchcraft.domain.usecase.RecipeCatalogUseCase;
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