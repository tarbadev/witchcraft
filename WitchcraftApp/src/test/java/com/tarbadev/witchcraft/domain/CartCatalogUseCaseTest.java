package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
  @Mock private CartRepository cartRepository;

  private CartCatalogUseCase subject;

  @Before
  public void setUp() {
    subject = new CartCatalogUseCase(cartRepository);
  }

  @Test
  public void execute() {
    List<Cart> carts = Arrays.asList(
        Cart.builder().build(),
        Cart.builder().build(),
        Cart.builder().build(),
        Cart.builder().build()
    );

    given(cartRepository.findAll()).willReturn(carts);

    assertThat(subject.execute()).isEqualTo(carts);
  }
}