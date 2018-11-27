package com.tarbadev.witchcraft.converter

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

class OzToLbConverterTest {
    private lateinit var ozToLbConverter: OzToLbConverter

    @BeforeEach
    fun setUp() {
        ozToLbConverter = OzToLbConverter()
    }

    @Test
    fun convert() {
        val oz = 16.0
        val expectedLb = 1.0

        assertEquals(expectedLb, ozToLbConverter.convert(oz))

        val oz2 = 20.0
        val expectedLb2 = 1.25

        assertEquals(expectedLb2, ozToLbConverter.convert(oz2))

        val oz3 = 5.0
        val expectedLb3 = 0.3125

        assertEquals(expectedLb3, ozToLbConverter.convert(oz3))
    }
}