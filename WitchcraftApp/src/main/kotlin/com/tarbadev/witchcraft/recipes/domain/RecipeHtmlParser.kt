package com.tarbadev.witchcraft.recipes.domain

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe

interface RecipeHtmlParser {
  fun parse(originUrl: String): Recipe
  fun isUrlSupported(url: String): Boolean
}