package com.tarbadev.witchcraft.web;

import com.tarbadev.witchcraft.domain.GetCurrentWeekUseCase;
import com.tarbadev.witchcraft.domain.RecipeCatalogUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeekController {
  private GetCurrentWeekUseCase getCurrentWeekUseCase;
  private RecipeCatalogUseCase recipeCatalogUseCase;

  public WeekController(GetCurrentWeekUseCase getCurrentWeekUseCase, RecipeCatalogUseCase recipeCatalogUseCase) {
    this.getCurrentWeekUseCase = getCurrentWeekUseCase;
    this.recipeCatalogUseCase = recipeCatalogUseCase;
  }

  @GetMapping("/weeks")
  public String index(Model model) {
    model.addAttribute("week", getCurrentWeekUseCase.execute());
    model.addAttribute("recipes", recipeCatalogUseCase.execute());
    return "weeks/index";
  }
}
