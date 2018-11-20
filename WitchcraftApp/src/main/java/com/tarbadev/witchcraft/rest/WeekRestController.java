package com.tarbadev.witchcraft.rest;

import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.usecase.SaveWeekUseCase;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weeks")
public class WeekRestController {
  private final WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;
  private final SaveWeekUseCase saveWeekUseCase;

  public WeekRestController(WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase, SaveWeekUseCase saveWeekUseCase) {
    this.weekFromYearAndWeekNumberUseCase = weekFromYearAndWeekNumberUseCase;
    this.saveWeekUseCase = saveWeekUseCase;
  }

  @GetMapping("/{year}/{weekNumber}")
  public Week getWeek(@PathVariable Integer year, @PathVariable Integer weekNumber) {
    return weekFromYearAndWeekNumberUseCase.execute(year, weekNumber);
  }

  @PostMapping("/{year}/{weekNumber}")
  public ResponseEntity<Object> saveWeek(@PathVariable Integer year, @PathVariable Integer weekNumber, @RequestBody Week week) {
    if (week.getWeekNumber().equals(weekNumber) && week.getYear().equals(year)) {
      return ResponseEntity.ok(saveWeekUseCase.execute(week));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
  }
}
