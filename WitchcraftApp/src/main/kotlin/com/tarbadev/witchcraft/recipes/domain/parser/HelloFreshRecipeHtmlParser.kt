package com.tarbadev.witchcraft.recipes.domain.parser

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
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
  override val supportedDomain: SupportedDomain =
      SupportedDomain(
          "HelloFresh",
          "https://www.hellofresh.com/recipes/",
          "https://cdn.hellofresh.com/logo/HelloFresh_Logo_Horizontal_V2.svg"
      )

  override val recipeNameSelector = "h1[data-test-id='recipeDetailFragment.recipe-name']"
  override val imgUrlSelector = "div > img"
  override val imgUrlAttribute = "src"
  override val ingredientSelector = "div.fela-_1qz307e"
  override val portionsSelector = "div.fela-_txm046 button"

  override fun getStepsFromHtml(html: Document): List<Step> {
    val steps = ArrayList<Step>()

    val htmlSteps = html.select("div.fela-_1qzip4i")
    for (htmlStep in htmlSteps) {
      steps.add(Step(name = htmlStep.text()))
    }

    return steps
  }
}