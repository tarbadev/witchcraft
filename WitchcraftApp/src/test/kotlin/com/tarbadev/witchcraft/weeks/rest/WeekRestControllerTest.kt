package com.tarbadev.witchcraft.weeks.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.*
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.*
import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase
import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase
import com.tarbadev.witchcraft.weeks.rest.entity.DayRequest
import com.tarbadev.witchcraft.weeks.rest.entity.MealRequest
import com.tarbadev.witchcraft.weeks.rest.entity.WeekRequest
import com.tarbadev.witchcraft.weeks.rest.entity.WeekResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(WeekRestController::class)
class WeekRestControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
  @MockBean
  private lateinit var weekFromYearAndWeekNumberUseCase: WeekFromYearAndWeekNumberUseCase
  @MockBean
  private lateinit var saveWeekUseCase: SaveWeekUseCase

  @BeforeEach
  fun setup() {
    reset(
        saveWeekUseCase,
        weekFromYearAndWeekNumberUseCase
    )
  }

  @Test
  fun getWeek() {
    val week = Week()

    whenever(weekFromYearAndWeekNumberUseCase.execute(2018, 33)).thenReturn(week)

    mockMvc.perform(get("/api/weeks/2018/33"))
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(objectMapper.writeValueAsString(WeekResponse.fromWeek(week))))
  }

  @Test
  fun saveWeek() {
    val week = Week(
        id = 1,
        year = 2018,
        weekNumber = 33,
        days = listOf(Day(id = 2, meals = listOf(Meal(id= 12, mealType = MealType.LUNCH, recipe = Recipe(id = 25))), name = DayName.FRIDAY))
    )

    val weekRequest = WeekRequest(
        id = 1,
        weekNumber = 33,
        year = 2018,
        days = listOf(DayRequest(id = 2, name = DayName.FRIDAY.name, lunch = listOf(MealRequest(12, 25))))
    )

    whenever(saveWeekUseCase.execute(any())).thenReturn(week)

    mockMvc.perform(post("/api/weeks/2018/33")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(weekRequest))
    )
        .andExpect(status().isOk)
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().json(objectMapper.writeValueAsString(WeekResponse.fromWeek(week))))

    verify(saveWeekUseCase).execute(week)
  }

  @Test
  fun saveWeek_returnsBadRequest_whenWeekAndYearDoNotMatch() {
    val week = Week(
        year = 2017,
        weekNumber = 30
    )

    mockMvc.perform(post("/api/weeks/2018/33")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(week))
    )
        .andExpect(status().isBadRequest)

    verify(saveWeekUseCase, never()).execute(week)
  }
}