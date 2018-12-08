package com.tarbadev.witchcraft.converter

class OzToLbConverter : IConverter {

    override fun convert(number: Double): Double {
        return number / OZ_IN_ONE_LB
    }

    companion object {
        private const val OZ_IN_ONE_LB = 16.0
    }
}
