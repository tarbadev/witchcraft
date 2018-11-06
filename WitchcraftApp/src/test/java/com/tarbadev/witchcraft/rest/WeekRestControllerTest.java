package com.tarbadev.witchcraft.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarbadev.witchcraft.domain.entity.Week;
import com.tarbadev.witchcraft.domain.usecase.CartCatalogUseCaseTest;
import com.tarbadev.witchcraft.domain.usecase.WeekFromYearAndWeekNumberUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeekRestControllerTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private WeekFromYearAndWeekNumberUseCase weekFromYearAndWeekNumberUseCase;

  @Test
  public void getWeek() throws Exception {
    Week week = Week.builder().build();

    given(weekFromYearAndWeekNumberUseCase.execute(2018, 33)).willReturn(week);

    mvc.perform(get("/api/weeks/2018/33"))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(week)));
  }
}