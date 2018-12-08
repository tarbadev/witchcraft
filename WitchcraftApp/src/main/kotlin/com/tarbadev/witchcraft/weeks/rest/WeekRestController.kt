package com.tarbadev.witchcraft.weeks.rest

import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase
import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/weeks")
class WeekRestController(private val weekFromYearAndWeekNumberUseCase: WeekFromYearAndWeekNumberUseCase, private val saveWeekUseCase: SaveWeekUseCase) {

    @GetMapping("/{year}/{weekNumber}")
    fun getWeek(@PathVariable year: Int?, @PathVariable weekNumber: Int?): Week {
        return weekFromYearAndWeekNumberUseCase.execute(year, weekNumber)
    }

    @PostMapping("/{year}/{weekNumber}")
    fun saveWeek(@PathVariable year: Int?, @PathVariable weekNumber: Int?, @RequestBody week: Week): ResponseEntity<Any> {
        return if (week.weekNumber == weekNumber && week.year == year) {
            ResponseEntity.ok(saveWeekUseCase.execute(week))
        } else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)

    }
}
