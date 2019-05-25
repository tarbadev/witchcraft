package com.tarbadev.witchcraft.weeks.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.weeks.domain.entity.Day
import com.tarbadev.witchcraft.weeks.domain.entity.Week
import com.tarbadev.witchcraft.weeks.domain.usecase.SaveWeekUseCase
import com.tarbadev.witchcraft.weeks.domain.usecase.WeekFromYearAndWeekNumberUseCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(WeekRestController::class)
class WeekRestControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    @MockBean private lateinit var weekFromYearAndWeekNumberUseCase: WeekFromYearAndWeekNumberUseCase
    @MockBean private lateinit var saveWeekUseCase: SaveWeekUseCase

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
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(ObjectMapper().writeValueAsString(week)))
    }

    @Test
    fun saveWeek() {
        val week = Week(
            year = 2018,
            weekNumber = 33,
            days = listOf(Day(lunch = (Recipe())))
        )

        whenever(saveWeekUseCase.execute(week)).thenReturn(week)

        mockMvc.perform(post("/api/weeks/2018/33")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(ObjectMapper().writeValueAsString(week))
        )
            .andExpect(status().isOk)
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(ObjectMapper().writeValueAsString(week)))
    }

    @Test
    fun saveWeek_returnsBadRequest_whenWeekAndYearDoNotMatch() {
        val week = Week(
            year = 2017,
            weekNumber = 30
        )

        mockMvc.perform(post("/api/weeks/2018/33")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(ObjectMapper().writeValueAsString(week))
        )
            .andExpect(status().isBadRequest)

        verify(saveWeekUseCase, never()).execute(week)
    }
}