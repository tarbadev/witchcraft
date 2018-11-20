package com.tarbadev.witchcraft.weeks.domain.usecase;

import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase;
import com.tarbadev.witchcraft.weeks.domain.entity.Day;
import com.tarbadev.witchcraft.weeks.domain.entity.DayName;
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.weeks.domain.entity.Week;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.weeks.domain.repository.WeekRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class SaveWeekUseCaseTest {
  @Mock private WeekRepository weekRepository;
  @Mock private RecipeRepository recipeRepository;

  private SaveWeekUseCase subject;

  @Before
  public void setUp() {
    subject = new SaveWeekUseCase(weekRepository, recipeRepository);
  }

  @Test
  public void execute() {
    Recipe lasagna = Recipe.builder().id(213).name("lasagna").build();
    Recipe tartiflette = Recipe.builder().id(897).name("tartiflette").build();

    Week week = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(Recipe.builder().id(213).build())
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(Recipe.builder().id(897).build())
                .build()
        ))
        .build();

    Week weekWithRecipes = Week.builder()
        .id(345)
        .year(2018)
        .weekNumber(12)
        .days(Arrays.asList(
            Day.builder()
                .id(234)
                .name(DayName.MONDAY)
                .lunch(lasagna)
                .build(),
            Day.builder()
                .id(789)
                .name(DayName.THURSDAY)
                .diner(tartiflette)
                .build()
        ))
        .build();

    given(recipeRepository.findById(213)).willReturn(lasagna);
    given(recipeRepository.findById(897)).willReturn(tartiflette);

    subject.execute(week);

    verify(weekRepository).save(weekWithRecipes);
  }
}