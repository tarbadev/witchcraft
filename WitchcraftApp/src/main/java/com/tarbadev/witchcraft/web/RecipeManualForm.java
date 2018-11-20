package com.tarbadev.witchcraft.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeManualForm {
  private String name;
  private String originUrl;
  private String imgUrl;
  private String ingredients;
  private String steps;
}
