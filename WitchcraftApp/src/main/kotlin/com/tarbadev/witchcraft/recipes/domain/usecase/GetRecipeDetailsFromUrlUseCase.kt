package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

import java.io.IOException
import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class GetRecipeDetailsFromUrlUseCase(
    private val ingredientFromStringUseCase: IngredientFromStringUseCase,
    private val convertAndAddSameIngredientUseCase: ConvertAndAddSameIngredientUseCase
) {

    fun execute(originUrl: String): Recipe {
        val html = getRecipeHtml(originUrl)
        val name = getRecipeNameFromHtml(html!!)
        val imgUrl = getImgUrl(html)
        val ingredients = getIngredientsFromHtml(html)
        val steps = getStepsFromHtml(html)

        return Recipe(
            name = name,
            originUrl = originUrl,
            imgUrl = imgUrl,
            ingredients = ingredients,
            steps = steps
        )
    }

    private fun getRecipeHtml(recipeUrl: String): Document? {
        var recipeHtml: Document? = null

        try {
            recipeHtml = Jsoup.connect(recipeUrl).get()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return recipeHtml
    }

    private fun getRecipeNameFromHtml(html: Document): String {
        val title = html.select("h1.entry-title")
        return title.text()
    }

    private fun getIngredientsFromHtml(html: Document): List<Ingredient> {
        val ingredients = ArrayList<Ingredient>()

        val htmlIngredients = html.select("div.recipe-ingredients-wrap ul li")
        for (htmlIngredient in htmlIngredients) {
            ingredients.add(ingredientFromStringUseCase.execute(htmlIngredient.text())!!)
        }

        return convertAndAddSameIngredientUseCase.execute(ingredients)
    }

    private fun getImgUrl(html: Document): String {
        val imgUrl = html.select("div.recipe-thumbnail img")
        return imgUrl.attr("data-lazy-src")
    }

    private fun getStepsFromHtml(html: Document): List<Step> {
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
