package com.tarbadev.witchcraft.persistence.entity;

import com.tarbadev.witchcraft.persistence.repository.IngredientEntity;
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
  private String originUrl;
  private String name;
  private String imgUrl;
  @Builder.Default
  private Boolean favorite = false;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<IngredientEntity> ingredients;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipe_id")
  private List<StepEntity> steps;
}
