package com.tarbadev.witchcraft.recipes.domain

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.*

@Component
class HelloFreshRecipeHtmlParser(
    ingredientFromStringUseCase: IngredientFromStringUseCase,
    convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) : RecipeHtmlParser(
    "www.hellofresh.com",
    ingredientFromStringUseCase,
    convertAndAddSameIngredientUseCase
) {
  override val recipeNameSelector = "h1[data-test-id='recipeDetailFragment.recipe-name']"
  override val imgUrlSelector = "img.fela-1f1h1of"
  override val imgUrlAttribute = "src"
  override val ingredientSelector = "div.fela-1qz307e"

  override fun getStepsFromHtml(html: Document): List<Step> {
    val steps = ArrayList<Step>()

    val htmlSteps = html.select("div.fela-1jua27u")
    for (htmlStep in htmlSteps) {
      steps.add(Step(name = htmlStep.text()))
    }

    return steps
  }
}