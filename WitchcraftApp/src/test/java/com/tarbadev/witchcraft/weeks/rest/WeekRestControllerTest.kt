package com.tarbadev.witchcraft.weeks.rest

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
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeekRestControllerTest(
    @Autowired private val testRestTemplate: TestRestTemplate
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

        val responseEntity = testRestTemplate.getForEntity("/api/weeks/2018/33", Week::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(week, responseEntity.body)
    }

    @Test
    fun saveWeek() {
        val week = Week(
            year = 2018,
            weekNumber = 33,
            days = listOf(Day(lunch = (Recipe())))
        )

        whenever(saveWeekUseCase.execute(week)).thenReturn(week)

        val responseEntity = testRestTemplate.postForEntity("/api/weeks/2018/33", week, Week::class.java)

        Assertions.assertEquals(HttpStatus.OK, responseEntity.statusCode)
        Assertions.assertEquals(MediaType.APPLICATION_JSON_UTF8, responseEntity.headers.contentType)
        Assertions.assertEquals(week, responseEntity.body)
    }

    @Test
    fun saveWeek_returnsBadRequest_whenWeekAndYearDoNotMatch() {
        val week = Week(
            year = 2017,
            weekNumber = 30
        )

        val responseEntity = testRestTemplate.postForEntity("/api/weeks/2018/33", week, Week::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)

        verify(saveWeekUseCase, never()).execute(week)
    }
}