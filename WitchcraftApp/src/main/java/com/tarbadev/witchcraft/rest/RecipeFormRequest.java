package com.tarbadev.witchcraft.rest;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class RecipeFormRequest {
  private String name;
  private String url;
  private String imageUrl;
  private String ingredients;
  private String steps;
}
