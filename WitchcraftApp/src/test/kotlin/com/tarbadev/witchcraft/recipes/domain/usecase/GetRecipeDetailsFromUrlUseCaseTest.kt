package com.tarbadev.witchcraft.recipes.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.tarbadev.witchcraft.recipes.domain.parser.CookinCanuckRecipeHtmlParser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecipeDetailsFromUrlUseCaseTest {
    private lateinit var subject: GetRecipeDetailsFromUrlUseCase

    private val cookinCanuckRecipeHtmlParser: CookinCanuckRecipeHtmlParser = mock()

    @BeforeEach
    fun setUp() {
        subject = GetRecipeDetailsFromUrlUseCase(listOf(cookinCanuckRecipeHtmlParser))

        reset(cookinCanuckRecipeHtmlParser)
    }

    @Test
    fun execute() {
        val url = "https://www.cookincanuck.com/some-fake-recipe/"

        whenever(cookinCanuckRecipeHtmlParser.isUrlSupported(url)).thenReturn(true)

        subject.execute(url)

        verify(cookinCanuckRecipeHtmlParser).parse(url)
    }
}