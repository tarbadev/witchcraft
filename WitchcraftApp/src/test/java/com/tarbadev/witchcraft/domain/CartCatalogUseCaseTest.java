package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
import com.tarbadev.witchcraft.domain.usecase.CartCatalogUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
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