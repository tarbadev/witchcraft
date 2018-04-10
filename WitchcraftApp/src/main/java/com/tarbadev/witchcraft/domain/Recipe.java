package com.tarbadev.witchcraft.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Value
@Builder
public class Recipe {
  private Integer id;
  private String url;
  private String name;
  private String imgUrl;
  private List<Ingredient> ingredients;
  private List<Step> steps;
}
