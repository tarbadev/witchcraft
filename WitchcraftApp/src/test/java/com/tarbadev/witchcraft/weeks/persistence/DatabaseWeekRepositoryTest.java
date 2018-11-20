package com.tarbadev.witchcraft.weeks.persistence;

import com.tarbadev.witchcraft.weeks.domain.entity.Day;
import com.tarbadev.witchcraft.weeks.domain.entity.DayName;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.weeks.domain.entity.Week;
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity;
import com.tarbadev.witchcraft.weeks.persistence.entity.WeekEntity;
import com.tarbadev.witchcraft.weeks.persistence.repository.DatabaseWeekRepository;
import com.tarbadev.witchcraft.weeks.persistence.repository.WeekEntityRepository;
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

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class DatabaseWeekRepositoryTest {
  @Autowired private WeekEntityRepository weekEntityRepository;
  @Autowired private TestEntityManager entityManager;

  private DatabaseWeekRepository subject;

  @Before
  public void setUp() {
    subject = new DatabaseWeekRepository(weekEntityRepository);
  }

  @Test
  public void findByYearAndWeekNumber() {
    WeekEntity week = entityManager.persistAndFlush(
        WeekEntity.builder()
            .days(Collections.emptyList())
            .year(2018)
            .weekNumber(24)
            .build()
    );

    entityManager.clear();

    assertThat(subject.findByYearAndWeekNumber(2018, 24)).isEqualToComparingFieldByFieldRecursively(week);
  }

  @Test
  public void save() {
    assertThat(weekEntityRepository.count()).isEqualTo(0);

    subject.save(Week.builder().days(Collections.emptyList()).build());

    assertThat(weekEntityRepository.count()).isEqualTo(1);
  }

  @Test
  public void save_updatesWeek() {
    RecipeEntity lasagnaEntity = entityManager.persist(
        RecipeEntity.builder()
            .name("lasagna")
            .originUrl("http://origin")
            .imgUrl("http://imgurl")
            .build()
    );
    RecipeEntity tartifletteEntity = entityManager.persist(
        RecipeEntity.builder()
            .name("tartiflette")
            .originUrl("http://origin")
            .imgUrl("http://imgurl")
            .build()
    );
    RecipeEntity racletteEntity = entityManager.persistAndFlush(
        RecipeEntity.builder()
            .name("raclette")
            .originUrl("http://origin")
            .imgUrl("http://imgurl")
            .build()
    );
    entityManager.clear();

    Recipe lasagna = Recipe.builder()
        .id(lasagnaEntity.getId())
        .url("/recipes/" + lasagnaEntity.getId())
        .originUrl("http://origin")
        .imgUrl("http://imgurl")
        .name("lasagna")
        .ingredients(Collections.emptyList())
        .steps(Collections.emptyList())
        .build();
    Recipe tartiflette = Recipe.builder()
        .id(tartifletteEntity.getId())
        .url("/recipes/" + tartifletteEntity.getId())
        .originUrl("http://origin")
        .imgUrl("http://imgurl")
        .name("tartiflette")
        .ingredients(Collections.emptyList())
        .steps(Collections.emptyList())
        .build();
    Recipe raclette = Recipe.builder()
        .id(racletteEntity.getId())
        .url("/recipes/" + racletteEntity.getId())
        .originUrl("http://origin")
        .imgUrl("http://imgurl")
        .name("raclette")
        .ingredients(Collections.emptyList())
        .steps(Collections.emptyList())
        .build();

    Week week = Week.builder()
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .name(DayName.MONDAY)
                .lunch(lasagna)
                .build(),
            Day.builder()
                .name(DayName.THURSDAY)
                .diner(tartiflette)
                .build()
        ))
        .build();

    subject.save(week);
    entityManager.clear();

    Week returnedWeek = subject.findByYearAndWeekNumber(week.getYear(), week.getWeekNumber());
    Week weekDomain = Week.builder()
        .id(returnedWeek.getId())
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(returnedWeek.getDays().get(0).getId())
                .name(DayName.MONDAY)
                .lunch(lasagna)
                .build(),
            Day.builder()
                .id(returnedWeek.getDays().get(1).getId())
                .name(DayName.THURSDAY)
                .diner(tartiflette)
                .build()
        ))
        .build();
    assertThat(returnedWeek).isEqualTo(weekDomain);

    Week updatedWeek = Week.builder()
        .id(returnedWeek.getId())
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(returnedWeek.getDays().get(0).getId())
                .name(DayName.MONDAY)
                .lunch(lasagna)
                .build(),
            Day.builder()
                .name(DayName.WEDNESDAY)
                .diner(raclette)
                .build()
        ))
        .build();

    subject.save(updatedWeek);
    entityManager.clear();

    Week returnedUpdatedWeek = subject.findByYearAndWeekNumber(updatedWeek.getYear(), updatedWeek.getWeekNumber());
    Week updatedWeekDomain = Week.builder()
        .id(returnedWeek.getId())
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(returnedWeek.getDays().get(0).getId())
                .name(DayName.MONDAY)
                .lunch(lasagna)
                .build(),
            Day.builder()
                .id(returnedUpdatedWeek.getDays().get(1).getId())
                .name(DayName.WEDNESDAY)
                .diner(raclette)
                .build()
        ))
        .build();

    assertThat(returnedUpdatedWeek).isEqualTo(updatedWeekDomain);
  }
}