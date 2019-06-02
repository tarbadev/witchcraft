package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain

data class SupportedDomainResponse(
    val name: String,
    val url: String,
    val imgUrl: String
) {
  companion object {
    fun fromSupportedDomain(supportedDomain: SupportedDomain) =
        SupportedDomainResponse(
            supportedDomain.name,
            supportedDomain.url,
            supportedDomain.imgUrl
        )
  }
}
