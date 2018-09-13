package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Cart;
import com.tarbadev.witchcraft.domain.repository.CartRepository;
import com.tarbadev.witchcraft.domain.usecase.GetCartUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class GetCartUseCaseTest {
  @Mock private CartRepository cartRepository;

  private GetCartUseCase subject;

  @Before
  public void setUp() {
    subject = new GetCartUseCase(cartRepository);
  }

  @Test
  public void execute() {
    Cart cart = Cart.builder().id(123).build();

    given(cartRepository.findById(123)).willReturn(cart);

    assertThat(subject.execute(123)).isEqualTo(cart);
  }
}