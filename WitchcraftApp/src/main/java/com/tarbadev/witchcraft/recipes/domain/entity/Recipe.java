package com.tarbadev.witchcraft.recipes.domain.entity;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
  @Builder.Default
  private Integer id = 0;
  @Builder.Default
  private String url = "";
  @Builder.Default
  private String originUrl = "";
  @Builder.Default
  private String name = "";
  @Builder.Default
  private String imgUrl = "";
  @Builder.Default
  private Boolean favorite = false;
  @Builder.Default
  private List<Ingredient> ingredients = Collections.emptyList();
  @Builder.Default
  private List<Step> steps = Collections.emptyList();
}
