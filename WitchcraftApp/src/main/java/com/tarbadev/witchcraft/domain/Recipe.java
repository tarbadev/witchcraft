package com.tarbadev.witchcraft.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Value
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String url;
  private String name;
  private String imgUrl;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<Ingredient> ingredients;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<Step> steps;
}
