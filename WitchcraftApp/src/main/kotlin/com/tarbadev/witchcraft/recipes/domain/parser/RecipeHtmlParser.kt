package com.tarbadev.witchcraft.recipes.domain.parser

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URI
import java.util.*

abstract class RecipeHtmlParser(
    private val supportedUrl: String,
    internal val ingredientFromStringUseCase: IngredientFromStringUseCase,
    internal val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) {
  abstract val recipeNameSelector: String
  abstract val imgUrlSelector: String
  abstract val imgUrlAttribute: String
  abstract val ingredientSelector: String
  abstract val portionsSelector: String

  abstract val supportedDomain: SupportedDomain
  abstract fun getStepsFromHtml(html: Document): List<Step>

  fun parse(originUrl: String): Recipe {
    val html = getRecipeHtml(originUrl)
    val name = getTextFromSelector(html, recipeNameSelector)
    val imgUrl = getImgUrl(html, imgUrlSelector, imgUrlAttribute)
    val ingredients = getIngredientsFromHtml(html)
    val steps = getStepsFromHtml(html)
    val portions = getPortionsFromHtml(html)

    return Recipe(
        name = name,
        originUrl = originUrl,
        imgUrl = imgUrl,
        ingredients = ingredients,
        steps = steps,
        portions = portions
    )
  }

  fun isUrlSupported(url: String): Boolean = URI(url).host == supportedUrl

  protected open fun getIngredientsFromHtml(html: Document): List<Ingredient> {
    val ingredients = ArrayList<Ingredient>()

    val htmlIngredients = html.select(ingredientSelector)
    for (htmlIngredient in htmlIngredients) {
      ingredients.add(ingredientFromStringUseCase.execute(htmlIngredient.text())!!)
    }

    return convertAndAddSameIngredientUseCase.execute(ingredients)
  }

  protected open fun getImgUrl(html: Document, selector: String, attribute: String): String {
    return html.select(selector).attr(attribute)
  }

  protected open fun getPortionsFromHtml(html: Document): Int {
    return getTextFromSelector(html, portionsSelector).toInt()
  }

  private fun getTextFromSelector(html: Document, selector: String): String {
    return html.select(selector)[0].text()
  }

  private fun getRecipeHtml(recipeUrl: String): Document {
    return Jsoup.connect(recipeUrl).get()
  }
}