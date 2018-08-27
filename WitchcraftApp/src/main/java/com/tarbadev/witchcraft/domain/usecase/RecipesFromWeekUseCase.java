package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Week;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class RecipesFromWeekUseCase {
  public List<Recipe> execute(Week week) {
    return week.getDays().stream()
        .map(day -> Arrays.asList(day.getLunch(), day.getDiner()).parallelStream()
            .filter(Objects::nonNull)
            .collect(toList()))
        .flatMap(List::stream)
        .collect(toList());
  }
}
