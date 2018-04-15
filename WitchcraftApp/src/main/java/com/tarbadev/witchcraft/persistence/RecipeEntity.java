package com.tarbadev.witchcraft.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recipes")
public class RecipeEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  private String url;
  private String name;
  private String imgUrl;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<IngredientEntity> ingredients;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<StepEntity> steps;
}
