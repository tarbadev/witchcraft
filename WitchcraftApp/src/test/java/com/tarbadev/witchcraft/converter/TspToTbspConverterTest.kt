package com.tarbadev.witchcraft.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TspToTbspConverterTest {
    private lateinit var tspToTbspConverter: TspToTbspConverter

    @BeforeEach
    fun setUp() {
        tspToTbspConverter = TspToTbspConverter()
    }

    @Test
    fun convert() {
        var tsp = 12.0
        var tbsp = 4.0

        assertEquals(tbsp, tspToTbspConverter.convert(tsp))

        tsp = 3.0
        tbsp = 1.0

        assertEquals(tbsp, tspToTbspConverter.convert(tsp))
    }
}
