package com.tarbadev.witchcraft.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.parseDouble;

@Component
public class GetRecipeDetailsFromUrlUseCase {
  public Recipe execute(String url) {
    Document html = getRecipeHtml(url);
    String name = getRecipeNameFromHtml(html);
    String imgUrl = getImgUrl(html);
    List<Ingredient> ingredients = getIngredientsFromHtml(html);
    List<Step> steps = getStepsFromHtml(html);

    return Recipe.builder()
        .name(name)
        .url(url)
        .imgUrl(imgUrl)
        .ingredients(ingredients)
        .steps(steps)
        .build();
  }

  private Document getRecipeHtml(String recipeUrl) {
    Document recipeHtml = null;

    try {
      recipeHtml = Jsoup.connect(recipeUrl).get();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return recipeHtml;
  }

  private String getRecipeNameFromHtml(Document html) {
    Elements title = html.select("h1.entry-title");
    return title.text();
  }

  private List<Ingredient> getIngredientsFromHtml(Document html) {
    List<Ingredient> ingredients = new ArrayList<>();

    Elements htmlIngredients = html.select("div.recipe-ingredients-wrap ul li");
    for (Element htmlIngredient : htmlIngredients) {
      Ingredient ingredient = Ingredient.getIngredientFromString(htmlIngredient.text());
      Optional<Ingredient> maybeIngredient = ingredients.stream()
          .filter(i ->
              i.getName().equals(ingredient.getName()) &&
                  i.getUnit().equals(ingredient.getUnit())
          ).findFirst();

      if (maybeIngredient.isPresent())
        maybeIngredient.get().addQuantity(ingredient.getQuantity());
      else
        ingredients.add(ingredient);
    }

    return ingredients;
  }

  private String getImgUrl(Document html) {
    Elements imgUrl = html.select("div.recipe-thumbnail img");
    return imgUrl.attr("src");
  }

  private List<Step> getStepsFromHtml(Document html) {
    List<Step> steps = new ArrayList<>();

    Elements htmlSteps = html.select("div.recipe-instructions ol li");
    for (Element htmlStep : htmlSteps) {
      steps.add(Step.builder().name(htmlStep.text()).build());
    }

    return steps;
  }
}
