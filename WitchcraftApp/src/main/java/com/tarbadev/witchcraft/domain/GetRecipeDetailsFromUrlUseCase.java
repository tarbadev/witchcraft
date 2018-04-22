package com.tarbadev.witchcraft.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetRecipeDetailsFromUrlUseCase {
  private IngredientFromStringUseCase ingredientFromStringUseCase;
  private ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase;

  public GetRecipeDetailsFromUrlUseCase(IngredientFromStringUseCase ingredientFromStringUseCase, ConvertAndAddSameIngredientUseCase convertAndAddSameIngredientUseCase) {
    this.ingredientFromStringUseCase = ingredientFromStringUseCase;
    this.convertAndAddSameIngredientUseCase = convertAndAddSameIngredientUseCase;
  }

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
      ingredients.add(ingredientFromStringUseCase.execute(htmlIngredient.text()));
    }

    return convertAndAddSameIngredientUseCase.execute(ingredients);
  }

  private String getImgUrl(Document html) {
    Elements imgUrl = html.select("div.recipe-thumbnail img");
    return imgUrl.attr("src");
  }

  private List<Step> getStepsFromHtml(Document html) {
    List<Step> steps = new ArrayList<>();

    Elements htmlSteps = html.select("div.recipe-instructions ol li");
    if (htmlSteps.size() > 0) {
      for (Element htmlStep : htmlSteps) {
        steps.add(Step.builder().name(htmlStep.text()).build());
      }
    } else {
      Elements paragraph = html.select("div.recipe-instructions p").attr("itemprop", "recipeIntructions");
      for (String step : paragraph.text().split("(\\d+\\.)")) {
        if (!step.isEmpty())
        steps.add(Step.builder().name(step.trim()).build());
      }
    }

    return steps;
  }
}
