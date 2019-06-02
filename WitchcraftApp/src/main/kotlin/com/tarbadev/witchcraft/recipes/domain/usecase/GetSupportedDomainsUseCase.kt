package com.tarbadev.witchcraft.recipes.domain.usecase

import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
import com.tarbadev.witchcraft.recipes.domain.parser.RecipeHtmlParser
import org.springframework.stereotype.Component

@Component
class GetSupportedDomainsUseCase(private val recipeHtmlParsers: List<RecipeHtmlParser>) {
  fun execute(): List<SupportedDomain> {
    return recipeHtmlParsers.map { it.supportedDomain }
  }
}
