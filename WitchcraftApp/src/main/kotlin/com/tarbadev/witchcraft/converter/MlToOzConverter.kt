package com.tarbadev.witchcraft.converter

class MlToOzConverter : IConverter {

    override fun convert(number: Double): Double {
        return number / ML_IN_ONE_OZ
    }

    companion object {
        private const val ML_IN_ONE_OZ = 29.5735
    }
}
