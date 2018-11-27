package com.tarbadev.witchcraft.converter

class TbspToCupConverter : IConverter {

    override fun convert(number: Double): Double {
        return number / TBSP_IN_ONE_CUP
    }

    companion object {
        private const val TBSP_IN_ONE_CUP = 16.0
    }
}
