package com.tarbadev.witchcraft.recipes.persistence.entity;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recipes")
public class RecipeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  public RecipeEntity(Recipe recipe) {
    id = recipe.getId();
    originUrl = recipe.getOriginUrl();
    name = recipe.getName();
    imgUrl = recipe.getImgUrl();
    favorite = recipe.getFavorite();
    ingredients = recipe.getIngredients().stream().map(IngredientEntity::new).collect(Collectors.toList());
    steps = recipe.getSteps().stream().map(StepEntity::new).collect(Collectors.toList());
  }

  public Recipe recipe() {
    return Recipe.builder()
        .id(id)
        .name(name.toLowerCase())
        .url("/recipes/" + id)
        .originUrl(originUrl)
        .imgUrl(imgUrl)
        .favorite(favorite)
        .ingredients(ingredients.stream().map(IngredientEntity::ingredient).collect(Collectors.toList()))
        .steps(steps.stream().map(StepEntity::step).collect(Collectors.toList()))
        .build();
  }
}
