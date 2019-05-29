package com.tarbadev.witchcraft.recipes.domain.parser

import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.usecase.ConvertAndAddSameIngredientUseCase
import com.tarbadev.witchcraft.recipes.domain.usecase.IngredientFromStringUseCase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import java.util.*
import java.util.regex.Pattern

@Component
class CookinCanuckRecipeHtmlParser(
    ingredientFromStringUseCase: IngredientFromStringUseCase,
    convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) : RecipeHtmlParser(
    "www.cookincanuck.com",
    ingredientFromStringUseCase,
    convertAndAddSameIngredientUseCase
) {
  override val recipeNameSelector = "h1.entry-title"
  override val imgUrlSelector = "div.recipe-thumbnail img"
  override val imgUrlAttribute = "data-lazy-src"
  override val ingredientSelector = "div.recipe-ingredients-wrap ul li"

  override fun getStepsFromHtml(html: Document): List<Step> {
    val steps = ArrayList<Step>()

    val htmlSteps = html.select("div.recipe-instructions ol li")
    if (htmlSteps.size > 0) {
      for (htmlStep in htmlSteps) {
        steps.add(Step(name = htmlStep.text()))
      }
    } else {
      val paragraph = html.select("div.recipe-instructions p").attr("itemprop", "recipeIntructions")
      val br2n = Jsoup.parse(paragraph.outerHtml().replace("(?i)<br[^>]*>".toRegex(), "br2n")).text()
      val paragraphs = br2n.replace("br2n".toRegex(), "\n")
      val pattern = Pattern.compile("( *\\d+\\. |)([\\w ()\\-,\\.é½⅓⅔¼¾⅛:]+)")
      for (step in paragraphs.split("(\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
        val matcher = pattern.matcher(step)
        if (matcher.find()) {
          steps.add(Step(name = matcher.group(2).trim { it <= ' ' }))
        } else {
          System.err.println(String.format("No match found: %s", step))
        }
      }
    }

    return steps
  }
}
