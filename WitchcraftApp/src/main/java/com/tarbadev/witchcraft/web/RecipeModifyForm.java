package com.tarbadev.witchcraft.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeModifyForm {
  private Integer id;
  private String url;
  private String name;
  private String imgUrl;
  private Double rating;
  private List<IngredientModifyForm> ingredients;
  private List<StepModifyForm> steps;
}
