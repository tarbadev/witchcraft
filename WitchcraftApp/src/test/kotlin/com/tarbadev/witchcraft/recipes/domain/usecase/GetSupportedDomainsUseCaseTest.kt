package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
import com.tarbadev.witchcraft.recipes.domain.parser.RecipeHtmlParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSupportedDomainsUseCaseTest {
  private val mockRecipeHtmlParser1: RecipeHtmlParser = mock()
  private val mockRecipeHtmlParser2: RecipeHtmlParser = mock()
  private val supportedDomain1 = SupportedDomain(
      "Some Vendor 1",
      "www.some.vendor.1.example.com/recipes",
      "www.some.vendor.1.example.com/logo.png"
  )
  private val supportedDomain2 = SupportedDomain(
      "2nd Some Vendor",
      "www.some.vendor.example.2.com/recipes",
      "www.some.vendor.example.2.com/logo.png"
  )

  private val subject = GetSupportedDomainsUseCase(listOf(
      mockRecipeHtmlParser1,
      mockRecipeHtmlParser2
  ))

  @BeforeEach
  fun setup() {
    whenever(mockRecipeHtmlParser1.supportedDomain).thenReturn(supportedDomain1)
    whenever(mockRecipeHtmlParser2.supportedDomain).thenReturn(supportedDomain2)
  }

  @Test
  fun execute() {
    val supportedDomains = listOf(
        supportedDomain1,
        supportedDomain2
    )

    assertThat(subject.execute()).isEqualTo(supportedDomains)
  }
}