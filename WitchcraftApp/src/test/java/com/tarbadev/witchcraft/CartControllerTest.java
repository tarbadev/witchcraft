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
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private CartCatalogUseCase cartCatalogUseCase;

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
}