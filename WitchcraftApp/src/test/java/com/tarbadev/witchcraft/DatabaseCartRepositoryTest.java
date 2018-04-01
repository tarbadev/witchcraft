package com.tarbadev.witchcraft;

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

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class DatabaseCartRepositoryTest {
    @Autowired private TestEntityManager entityManager;
    @Autowired private CartRepository cartRepository;

    private DatabaseCartRepository subject;

    @Before
    public void setUp() {
        subject = new DatabaseCartRepository(cartRepository);
    }

    @Test
    public void test_findAll_ReturnsAllCarts() {
        entityManager.persist(Cart.builder().build());
        entityManager.persist(Cart.builder().build());
        entityManager.persist(Cart.builder().build());

        entityManager.flush();
        entityManager.clear();

        assertThat(subject.findAll().size()).isEqualTo(3);
    }
}