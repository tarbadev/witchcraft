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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartCatalogUseCaseTest {
    @Autowired private DatabaseCartRepository databaseCartRepository;

    private CartCatalogUseCase subject;

    @Before
    public void setUp() {
        subject = new CartCatalogUseCase(databaseCartRepository);
        Mockito.reset(databaseCartRepository);
    }

    @Test
    public void test_execute_ReturnsCarts() {
        List<Cart> carts = Arrays.asList(
                Cart.builder().build(),
                Cart.builder().build(),
                Cart.builder().build(),
                Cart.builder().build()
        );

        given(databaseCartRepository.findAll()).willReturn(carts);

        assertThat(subject.execute()).isEqualTo(carts);
    }
}