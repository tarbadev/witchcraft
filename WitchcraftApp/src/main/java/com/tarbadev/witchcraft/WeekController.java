package com.tarbadev.witchcraft;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeekController {
  private GetCurrentWeekUseCase getCurrentWeekUseCase;

  public WeekController(GetCurrentWeekUseCase getCurrentWeekUseCase) {
    this.getCurrentWeekUseCase = getCurrentWeekUseCase;
  }

  @GetMapping("/weeks")
  public String index(Model model) {
    model.addAttribute("week", getCurrentWeekUseCase.execute());
    return "weeks/index";
  }
}
