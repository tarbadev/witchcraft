package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class Cart {
  private Integer id;
  protected LocalDateTime createdAt;
  private List<Recipe> recipes;
  private List<Item> items;
}
