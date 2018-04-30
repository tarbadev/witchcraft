package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WeekController {
  private RecipeCatalogUseCase recipeCatalogUseCase;
  private SaveWeekUseCase saveWeekUseCase;
  private WeekNavForWeekUseCase weekNavForWeekUseCase;
  private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;
  private RecipesFromWeekUseCase recipesFromWeekUseCase;
  private CreateCartUseCase createCartUseCase;

  public WeekController(RecipeCatalogUseCase recipeCatalogUseCase, SaveWeekUseCase saveWeekUseCase, WeekNavForWeekUseCase weekNavForWeekUseCase, WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase, RecipesFromWeekUseCase recipesFromWeekUseCase, CreateCartUseCase createCartUseCase) {
    this.recipeCatalogUseCase = recipeCatalogUseCase;
    this.saveWeekUseCase = saveWeekUseCase;
    this.weekNavForWeekUseCase = weekNavForWeekUseCase;
    this.weekFromYearAndWeekNumberUseCase = weekFromYearAndWeekNumberUseCase;
    this.recipesFromWeekUseCase = recipesFromWeekUseCase;
    this.createCartUseCase = createCartUseCase;
  }

  @GetMapping("/weeks")
  public String index(Model model) {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    return String.format("redirect:/weeks/%s/%s", year, weekNumber);
  }

  @PatchMapping("/weeks/{year}/{weekNumber}/save")
  public String save(@PathVariable Integer year, @PathVariable Integer weekNumber, @Valid WeekForm weekForm) {
    Week week = Week.builder()
        .id(weekForm.getId())
        .year(year)
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

    return String.format("redirect:/weeks/%s/%s", year, weekNumber);
  }

  @GetMapping("/weeks/{year}/{weekNumber}")
  public String show(Model model, @PathVariable Integer year, @PathVariable Integer weekNumber) {
    Week week = weekFromYearAndWeekNumberUseCase.execute(year, weekNumber);
    model.addAttribute("week", week);
    model.addAttribute("weekForm", WeekForm.builder()
        .id(week.getId())
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
    WeekNav weekNav = weekNavForWeekUseCase.execute(week);
    model.addAttribute("weekNav", weekNav);
    model.addAttribute("recipes", recipeCatalogUseCase.execute());

    return "weeks/index";
  }

  @GetMapping("/weeks/{year}/{weekNumber}/createCart")
  public String createCart(Model model, @PathVariable Integer year, @PathVariable Integer weekNumber) {
    Week week = weekFromYearAndWeekNumberUseCase.execute(year, weekNumber);
    List<Recipe> recipes = recipesFromWeekUseCase.execute(week);
    Cart cart = createCartUseCase.execute(recipes);

    return String.format("redirect:/carts/%s", cart.getId());
  }
}
