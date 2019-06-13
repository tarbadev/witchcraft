package com.tarbadev.witchcraft.weeks.rest

import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase
import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase
import com.tarbadev.witchcraft.weeks.rest.entity.WeekRequest
import com.tarbadev.witchcraft.weeks.rest.entity.WeekResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/weeks/{year}/{weekNumber}")
class WeekRestController(
    private val weekFromYearAndWeekNumberUseCase: WeekFromYearAndWeekNumberUseCase,
    private val saveWeekUseCase: SaveWeekUseCase
) {

  @GetMapping
  fun getWeek(@PathVariable year: Int?, @PathVariable weekNumber: Int?): WeekResponse {
    return WeekResponse.fromWeek(weekFromYearAndWeekNumberUseCase.execute(year, weekNumber))
  }

  @PostMapping
  fun saveWeek(@PathVariable year: Int, @PathVariable weekNumber: Int, @RequestBody week: WeekRequest): ResponseEntity<WeekResponse> {
    return if (week.weekNumber == weekNumber && week.year == year) {
      ResponseEntity.ok(WeekResponse.fromWeek(saveWeekUseCase.execute(week.toWeek())))
    } else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
  }
}
