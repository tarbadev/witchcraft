package com.tarbadev.witchcraft.converter

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

class TbspToCupConverterTest {
    private lateinit var tbspToCupConverter: TbspToCupConverter

    @BeforeEach
    fun setUp() {
        tbspToCupConverter = TbspToCupConverter()
    }

    @Test
    fun convert() {
        var tbsp = 1.0
        var expectedCup = 0.0625

        assertEquals(expectedCup, tbspToCupConverter.convert(tbsp))

        tbsp = 4.0
        expectedCup = 0.25

        assertEquals(expectedCup, tbspToCupConverter.convert(tbsp))
    }
}