package com.tarbadev.witchcraft.recipes.domain.parser

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.ArrayList

@Component
class MarmitonRecipeHtmlParser(
    ingredientFromStringUseCase: IngredientFromStringUseCase,
    convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) : RecipeHtmlParser(
    "www.marmiton.org",
    ingredientFromStringUseCase,
    convertAndAddSameIngredientUseCase
) {
  override val recipeNameSelector = "h1.main-title"
  override val imgUrlSelector: String = "img#af-diapo-desktop-0_img"
  override val imgUrlAttribute: String = "src"
  override val ingredientSelector: String = ".recipe-ingredients__list__item div"

  override fun getStepsFromHtml(html: Document): List<Step> {
    val steps = ArrayList<Step>()

    val htmlSteps = html.select("li.recipe-preparation__list__item")
    for (htmlStep in htmlSteps) {
      val text = htmlStep.text()
      steps.add(Step(name = text.replace("(Etape \\d+ )".toRegex(), "")))
    }

    return steps
  }
}