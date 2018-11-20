package com.tarbadev.witchcraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarbadev.witchcraft.domain.entity.Day;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.usecase.SaveWeekUseCase;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeekRestControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;
  @Autowired
  private SaveWeekUseCase saveWeekUseCase;

  @Before
  public void setup() {
    reset(
        saveWeekUseCase,
        weekFromYearAndWeekNumberUseCase
    );
  }

  @Test
  public void getWeek() throws Exception {
    Week week = Week.builder().build();

    given(weekFromYearAndWeekNumberUseCase.execute(2018, 33)).willReturn(week);

    mvc.perform(get("/api/weeks/2018/33"))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(week)));
  }

  @Test
  public void saveWeek() throws Exception {
    Week week = Week.builder()
        .year(2018)
        .weekNumber(33)
        .days(Collections.singletonList(Day.builder().lunch(Recipe.builder().build()).build()))
        .build();

    given(saveWeekUseCase.execute(week)).willReturn(week);

    mvc.perform(post("/api/weeks/2018/33")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(week))
    )
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(week)));
  }

  @Test
  public void saveWeek_returnsBadRequest_whenWeekAndYearDoNotMatch() throws Exception {
    Week week = Week.builder()
        .year(2017)
        .weekNumber(30)
        .build();

    mvc.perform(post("/api/weeks/2018/33")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(week))
    )
        .andExpect(status().isBadRequest());

    verify(saveWeekUseCase, never()).execute(any());
  }
}