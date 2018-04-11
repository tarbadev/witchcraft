package com.tarbadev.witchcraft.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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