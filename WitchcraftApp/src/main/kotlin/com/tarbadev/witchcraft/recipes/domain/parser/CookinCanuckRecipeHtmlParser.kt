package com.tarbadev.witchcraft.recipes.domain.parser

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.*

@Component
class CookinCanuckRecipeHtmlParser(
    ingredientFromStringUseCase: IngredientFromStringUseCase,
    convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) : RecipeHtmlParser(
    "www.cookincanuck.com",
    ingredientFromStringUseCase,
    convertAndAddSameIngredientUseCase
) {
  override val supportedDomain: SupportedDomain =
    SupportedDomain(
        "Cookin Canuck",
        "https://www.cookincanuck.com",
        "https://www.cookincanuck.com/wp-content/uploads/2017/09/logo.png"
    )

  override val recipeNameSelector = "h1.entry-title"
  override val imgUrlSelector = "div.wprm-recipe-image img"
  override val imgUrlAttribute = "data-lazy-src"
  override val ingredientSelector = "li.wprm-recipe-ingredient"

  override fun getIngredientsFromHtml(html: Document): List<Ingredient> {
    val ingredients = ArrayList<Ingredient>()

    val htmlIngredients = html.select(ingredientSelector)
    for (htmlIngredient in htmlIngredients) {
      val unit = htmlIngredient.select("span.wprm-recipe-ingredient-unit").text()
      val amount = htmlIngredient.select("span.wprm-recipe-ingredient-amount").text()
      val name = htmlIngredient.select("span.wprm-recipe-ingredient-name").text()
      val notes = htmlIngredient.select("span.wprm-recipe-ingredient-notes").text()
      ingredients.add(ingredientFromStringUseCase.execute("$amount $unit $name $notes")!!)
    }

    return convertAndAddSameIngredientUseCase.execute(ingredients)
  }

  override fun getStepsFromHtml(html: Document): List<Step> {
    val steps = ArrayList<Step>()

    val htmlSteps = html.select("div.wprm-recipe-instruction-text")
    if (htmlSteps.size > 0) {
      for (htmlStep in htmlSteps) {
        steps.add(Step(name = htmlStep.text()))
      }
    }

    return steps
  }
}
