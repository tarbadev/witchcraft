package com.tarbadev.witchcraft.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TspToCupConverterTest {
    private lateinit var tspToCupConverter: TspToCupConverter

    @BeforeEach
    fun setUp() {
        tspToCupConverter = TspToCupConverter()
    }

    @Test
    fun convert() {
        var tsp = 48.0
        var cup = 1.0

        assertEquals(cup, tspToCupConverter.convert(tsp))

        tsp = 3.0
        cup = 0.0625

        assertEquals(cup, tspToCupConverter.convert(tsp))
    }
}
