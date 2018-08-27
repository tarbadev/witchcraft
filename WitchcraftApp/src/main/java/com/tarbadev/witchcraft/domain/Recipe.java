package com.tarbadev.witchcraft.domain;

import lombok.*;

import java.util.List;

@Value
@Builder
public class Recipe {
  private Integer id;
  private String originUrl;
  private String name;
  private String imgUrl;
  private Double rating;
  private List<Ingredient> ingredients;
  private List<Step> steps;
}
