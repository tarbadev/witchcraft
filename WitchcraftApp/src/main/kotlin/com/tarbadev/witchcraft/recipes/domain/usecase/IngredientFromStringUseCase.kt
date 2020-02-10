package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.converter.UnitHelper.getQuantity
import com.tarbadev.witchcraft.converter.UnitHelper.getUnitMappingByShortName
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import opennlp.tools.postag.POSModel
import opennlp.tools.postag.POSTaggerME
import opennlp.tools.tokenize.SimpleTokenizer
import org.springframework.stereotype.Component

@Component
class IngredientFromStringUseCase(private val saveIngredientToIngredientLearningSourceUseCase: SaveIngredientToIngredientLearningSourceUseCase) {

  fun execute(text: String): Ingredient? {
    val fixedText = fixText(text)

    val tokens = SimpleTokenizer.INSTANCE.tokenize(fixedText).toMutableList()
    val tags = extractTags(tokens)

    var currentIndex = tags.indexOfFirst { it == "CD" }
    val quantity = extractQuantity(tags, tokens, currentIndex)

    if (currentIndex <= 0) {
      currentIndex = 0
    }

    val unit = extractUnit(tokens, currentIndex)

    if (tokens[0] == "de" || tokens[0] == ".") {
      tokens.removeAt(0)
    }

    if (tokens[0] == "d" && tokens[1] == "'") {
      tokens.removeAt(0)
      tokens.removeAt(0)
    }

    val name = tokens.joinToString(" ")
        .replace("( ", "(")
        .replace(" )", ")")
        .replace(" .", ".")
        .replace(" - ", "-")
        .replace(" , ", ", ")

    val ingredient = Ingredient(
        name = name,
        quantity = getQuantity(quantity, unit)
    )

    saveIngredientToIngredientLearningSourceUseCase.execute(text, ingredient)

    return ingredient
  }

  private fun extractUnit(tokens: MutableList<String>, currentIndex: Int): String {
    var unit = tokens[currentIndex]

    if (unit.endsWith("s")) {
      unit = unit.substring(0, unit.length - 1)
    }

    if (getUnitMappingByShortName(unit) != null) {
      tokens.removeAt(currentIndex)
    } else {
      unit = ""
    }

    return unit
  }

  private fun fixText(text: String): String {
    return text
        .replace("⁄", "/")
        .replace("½", "1/2")
        .replace("⅓", "1/3")
        .replace("⅔", "2/3")
        .replace("¼", "1/4")
        .replace("¾", "3/4")
        .replace("⅛", "1/8")
        .replace("pound", "lb")
        .replace("ounce", "oz")
        .replace("teaspoon", "tsp")
        .replace("tablespoon", "tbsp")
        .replace("cuillère à soupe", "tbsp")
        .replace("cuillères à soupe", "tbsp")
        .replace("(ContainsMilk)", "")
        .replace("(ContainsWheat)", "")
        .toLowerCase()
  }

  private fun extractQuantity(tags: Array<String>, tokens: MutableList<String>, currentIndex: Int): Double {
    var quantity = 1.0
    if (tags.count { it == "CD" } >= 2) {
      if (tokens[currentIndex + 1] == "/" && tags[currentIndex + 2] == "CD") {
        val firstNumber = tokens[currentIndex].toDouble()
        val secondNumber = tokens[currentIndex + 2].toDouble()
        quantity = firstNumber / secondNumber
        tokens.removeAt(currentIndex)
        tokens.removeAt(currentIndex)
        tokens.removeAt(currentIndex)
      } else if (tags[currentIndex + 1] == "CD" && tokens[currentIndex + 2] == "/" && tags[currentIndex + 3] == "CD") {
        val firstNumber = tokens[currentIndex].toDouble()
        val secondNumber = tokens[currentIndex + 1].toDouble()
        val thirdNumber = tokens[currentIndex + 3].toDouble()
        quantity = firstNumber + (secondNumber / thirdNumber)
        tokens.removeAt(currentIndex)
        tokens.removeAt(currentIndex)
        tokens.removeAt(currentIndex)
        tokens.removeAt(currentIndex)
      }
    } else if (currentIndex >= 0) {
      quantity = tokens[currentIndex].toDouble()
      tokens.removeAt(currentIndex)
    }
    return quantity
  }

  private fun extractTags(tokens: MutableList<String>): Array<String> {
    val inputStreamPOSTagger = javaClass.getResourceAsStream("/models/en-pos-maxent.bin")
    val posModel = POSModel(inputStreamPOSTagger)
    val posTagger = POSTaggerME(posModel)
    return posTagger.tag(tokens.toTypedArray())
  }
}
