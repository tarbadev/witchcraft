package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.RecipeHtmlParser
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import org.springframework.stereotype.Component

@Component
class GetRecipeDetailsFromUrlUseCase(
    private val recipeHtmlParsers: List<RecipeHtmlParser>
) {

    fun execute(url: String): Recipe {
        return recipeHtmlParsers.first { it.isUrlSupported(url) }.parse(url)
    }
}
