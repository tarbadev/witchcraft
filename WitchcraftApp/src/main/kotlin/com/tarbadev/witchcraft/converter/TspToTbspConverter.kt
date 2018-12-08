package com.tarbadev.witchcraft.converter

class TspToTbspConverter : IConverter {

    override fun convert(number: Double): Double {
        return number / TSP_IN_ONE_TBSP
    }

    companion object {
        private const val TSP_IN_ONE_TBSP = 3.0
    }
}
