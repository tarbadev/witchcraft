package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.BestRatedRecipesUseCase;
import com.tarbadev.witchcraft.domain.LastAddedRecipesUseCase;
import com.tarbadev.witchcraft.domain.WeekFromYearAndWeekNumberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;

@Controller
public class HomeController {
  @Autowired private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;
  @Autowired private BestRatedRecipesUseCase bestRatedRecipesUseCase;
  @Autowired private LastAddedRecipesUseCase lastAddedRecipesUseCase;

  @GetMapping("/")
  public String index(Model model) {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

    model.addAttribute("currentWeek", weekFromYearAndWeekNumberUseCase.execute(year, weekNumber));
    model.addAttribute("bestRecipes", bestRatedRecipesUseCase.execute());
    model.addAttribute("lastAddedRecipes", lastAddedRecipesUseCase.execute());

    return "home/index";
  }
}
