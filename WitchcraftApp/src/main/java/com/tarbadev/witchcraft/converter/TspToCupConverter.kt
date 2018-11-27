package com.tarbadev.witchcraft.converter

class TspToCupConverter : IConverter {

    override fun convert(number: Double): Double {
        return number / TSP_IN_ONE_CUP
    }

    companion object {
        private const val TSP_IN_ONE_CUP = 48.0
    }
}
