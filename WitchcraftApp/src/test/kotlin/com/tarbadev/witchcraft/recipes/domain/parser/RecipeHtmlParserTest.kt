package com.tarbadev.witchcraft.recipes.domain.parser

import com.nhaarman.mockitokotlin2.mock
import com.tarbadev.witchcraft.TestResources
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test

class RecipeHtmlParserTest {
  private val recipeSteps = TestResources.Recipes.cookinCanuck.steps
  private val supportedUrl = "www.example.com"
  private val subject = FakeRecipeHtmlParser()

  @Test
  fun isUrlSupported() {
    assertThat(subject.isUrlSupported("https://$supportedUrl/fake-recipe-url")).isTrue()
  }

  inner class FakeRecipeHtmlParser(
      override val recipeNameSelector: String = "",
      override val imgUrlSelector: String = "",
      override val imgUrlAttribute: String = "",
      override val ingredientSelector: String = ""
  ) : RecipeHtmlParser(supportedUrl, mock(), mock()) {
    override fun getStepsFromHtml(html: Document): List<Step> = recipeSteps
  }
}