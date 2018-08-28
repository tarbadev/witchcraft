package com.tarbadev.witchcraft.domain.entity;

import lombok.*;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Recipe {
  private Integer id;
  private String url;
  private String originUrl;
  private String name;
  private String imgUrl;
  private Double rating;
  private List<Ingredient> ingredients;
  private List<Step> steps;
}
