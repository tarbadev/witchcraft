package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class WeekController {
  private GetCurrentWeekUseCase getCurrentWeekUseCase;
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private SaveWeekUseCase saveWeekUseCase;

  public WeekController(GetCurrentWeekUseCase getCurrentWeekUseCase, RecipeCatalogUseCase recipeCatalogUseCase, SaveWeekUseCase saveWeekUseCase) {
    this.getCurrentWeekUseCase = getCurrentWeekUseCase;
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.saveWeekUseCase = saveWeekUseCase;
  }

  @GetMapping("/weeks")
  public String index(Model model) {
    Week week = getCurrentWeekUseCase.execute();
    model.addAttribute("week", week);
    model.addAttribute("weekForm", WeekForm.builder()
        .id(week.getId())
        .year(week.getYear())
        .days(week.getDays().stream()
            .map(day -> DayForm.builder()
                .id(day.getId())
                .name(day.getName())
                .lunchId(day.getLunch() != null ? day.getLunch().getId() : null)
                .dinerId(day.getDiner() != null ? day.getDiner().getId() : null)
                .build())
            .collect(Collectors.toList())
        )
        .build());
    model.addAttribute("recipes", recipeCatalogUseCase.execute());
    return "weeks/index";
  }

  @PatchMapping("/weeks/{weekNumber}/save")
  public String save(@PathVariable Integer weekNumber, @Valid WeekForm weekForm) {
    Week week = Week.builder()
        .id(weekForm.getId())
        .year(weekForm.getYear())
        .weekNumber(weekNumber)
        .days(weekForm.getDays().stream()
            .map(dayForm -> Day.builder()
                .id(dayForm.getId())
                .name(dayForm.getName())
                .lunch(dayForm.getLunchId() != null ? Recipe.builder().id(dayForm.getLunchId()).build() : null)
                .diner(dayForm.getDinerId() != null ? Recipe.builder().id(dayForm.getDinerId()).build() : null)
                .build())
            .collect(Collectors.toList()))
        .build();

    saveWeekUseCase.execute(week);

    return "redirect:/weeks";
  }
}
