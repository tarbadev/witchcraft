package com.tarbadev.witchcraft.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeFormRequest {
  private String name;
  private String url;
  private String imageUrl;
  private String ingredients;
  private String steps;
}