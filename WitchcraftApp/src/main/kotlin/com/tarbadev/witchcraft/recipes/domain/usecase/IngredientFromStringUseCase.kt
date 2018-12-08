package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import org.springframework.stereotype.Component
import java.lang.Double.parseDouble
import java.util.*
import java.util.regex.Pattern

@Component
class IngredientFromStringUseCase {

    fun execute(text: String): Ingredient? {
        val newText = text
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
        var quantity = 0.0
        var unit = ""
        var name = ""
        val pattern = Pattern.compile("([\\d ]*\\d+[[\\⁄\\/]\\d]*|\\d+|)([\\s]|)(([A-Za-z]+?|[A-Za-z]+?)(s\\b|\\b)|)([ .,]*)([()\\w ,-\\.\\/&\\'!\\+]+)")
        val matcher = pattern.matcher(newText)
        if (matcher.find()) {
            val quantityStr = matcher.group(1)
            if (!quantityStr.isEmpty()) {
                val numbers = quantityStr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (number in numbers) {
                    var tempQuantity: Double? = null
                    try {
                        tempQuantity = parseDouble(number)
                    } catch (ignored: NumberFormatException) {
                    }

                    when {
                        tempQuantity != null -> quantity += tempQuantity
                        number.contains("/") -> {
                            val fraction = number.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            tempQuantity = parseDouble(fraction[0]) / parseDouble(fraction[1])
                            quantity += tempQuantity
                        }
                        number.contains("⁄") -> {
                            val fraction = number.split("⁄".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            tempQuantity = parseDouble(fraction[0]) / parseDouble(fraction[1])
                            quantity += tempQuantity
                        }
                    }
                }
            }

            if (matcher.group(4) != null) {
                val tempUnit = matcher.group(4)
                    .toLowerCase()
                    .replace("\\.".toRegex(), "")
                if (SUPPORTED_UNITS.contains(tempUnit)) {
                    unit = tempUnit
                } else if (!tempUnit.isEmpty()) {
                    name = tempUnit + matcher.group(5) + matcher.group(6)
                }
            }

            name += matcher.group(7)

            return Ingredient(
                name = name,
                quantity = quantity,
                unit = unit
            )
        } else {
            System.err.println("Ingredient not supported: $newText")

            return null
        }
    }

    companion object {
        private val SUPPORTED_UNITS = Arrays.asList(
            "lb",
            "oz",
            "tsp",
            "tbsp",
            "cup"
        )
    }
}
