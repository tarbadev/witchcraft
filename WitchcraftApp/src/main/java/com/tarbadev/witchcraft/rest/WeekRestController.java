package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weeks")
public class WeekRestController {
  private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;

  public WeekRestController(WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase) {
    this.weekFromYearAndWeekNumberUseCase = weekFromYearAndWeekNumberUseCase;
  }

  @GetMapping("/{year}/{week}")
  public Week getWeek(@PathVariable Integer year, @PathVariable Integer week) {
    return weekFromYearAndWeekNumberUseCase.execute(year, week);
  }
}
