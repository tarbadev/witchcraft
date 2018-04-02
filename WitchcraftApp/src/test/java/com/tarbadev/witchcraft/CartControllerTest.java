package com.tarbadev.witchcraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTest {
  @Autowired private MockMvc mvc;
  @Autowired private CartCatalogUseCase cartCatalogUseCase;
  @Autowired private RecipeCatalogUseCase recipeCatalogUseCase;
  @Autowired private CreateCartUseCase createCartUseCase;


  @Before
  public void setUp() {
    Mockito.reset(cartCatalogUseCase);
  }


  @Test
  public void test_index_ShowsAllCarts() throws Exception {
    List<Cart> carts = Arrays.asList(
        Cart.builder().build(),
        Cart.builder().build(),
        Cart.builder().build(),
        Cart.builder().build()
    );

    given(cartCatalogUseCase.execute()).willReturn(carts);

    mvc.perform(get("/carts"))
        .andExpect(status().isOk())
        .andExpect(view().name("carts/index"))
        .andExpect(model().attribute("carts", carts));
  }

  @Test
  public void test_showNewCart_displaysNewCartForm() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().build(),
        Recipe.builder().build(),
        Recipe.builder().build()
    );
    NewCartForm newCartForm = new NewCartForm(Collections.emptyList());

    given(recipeCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(get("/carts/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("carts/newCart"))
        .andExpect(model().attribute("newCartForm", newCartForm))
        .andExpect(model().attribute("recipes", recipes));
  }

  @Test
  public void test_newCart_createsNewCart() throws Exception {
    List<Recipe> recipes = Arrays.asList(
        Recipe.builder().id(1).build(),
        Recipe.builder().id(2).build(),
        Recipe.builder().id(3).build()
    );

    NewCartForm newCartForm = new NewCartForm(Arrays.asList(1, 3));

    given(recipeCatalogUseCase.execute()).willReturn(recipes);

    mvc.perform(
        post("/carts/new")
            .flashAttr("newCartForm", newCartForm)
    ).andExpect(redirectedUrl("/carts"));

    verify(recipeCatalogUseCase).execute();
    verify(createCartUseCase).execute(Arrays.asList(recipes.get(0), recipes.get(2)));
  }
}