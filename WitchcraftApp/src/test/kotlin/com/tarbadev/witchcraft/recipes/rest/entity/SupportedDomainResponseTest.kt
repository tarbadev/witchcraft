package com.tarbadev.witchcraft.recipes.rest.entity

import com.tarbadev.witchcraft.recipes.domain.entity.SupportedDomain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SupportedDomainResponseTest {
  @Test
  fun fromDomain() {
    val supportedDomain = SupportedDomain(
        "Some Vendor",
        "www.some.vendor.example.com/recipes",
        "www.some.vendor.example.com/logo.png"
    )
    val supportedDomainResponse = SupportedDomainResponse(
        "Some Vendor",
        "www.some.vendor.example.com/recipes",
        "www.some.vendor.example.com/logo.png"
    )
    assertThat(SupportedDomainResponse.fromSupportedDomain(supportedDomain)).isEqualTo(supportedDomainResponse)
  }
}