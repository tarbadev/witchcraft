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
  @Builder.Default
  private Boolean favorite = false;
  private List<IngredientModifyForm> ingredients;
  private List<StepModifyForm> steps;
}
