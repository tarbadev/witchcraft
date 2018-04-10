package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Week;
import com.tarbadev.witchcraft.persistence.DatabaseWeekRepository;
import com.tarbadev.witchcraft.persistence.WeekRepository;
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

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class DatabaseWeekRepositoryTest {
  @Autowired private WeekRepository weekRepository;
  @Autowired private TestEntityManager entityManager;

  private DatabaseWeekRepository subject;

  @Before
  public void setUp() {
    subject = new DatabaseWeekRepository(weekRepository);
  }

  @Test
  public void findByYearAndWeekNumber() {
    Week week = entityManager.persistAndFlush(
        Week.builder()
            .days(Collections.emptyList())
            .year(2018)
            .weekNumber(24)
            .build()
    );

    entityManager.clear();

    assertThat(subject.findByYearAndWeekNumber(2018, 24)).isEqualToComparingFieldByFieldRecursively(week);
  }
}