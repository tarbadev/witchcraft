package com.tarbadev.witchcraft

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WitchcraftConfig(
    @Value("\${witchcraft.frontend.url}") private val allowedOrigin: Array<String>
) : WebMvcConfigurer {

  override fun addCorsMappings(registry: CorsRegistry) {
    val corsRegistration = registry.addMapping("/**")
    corsRegistration.allowedOrigins(*allowedOrigin).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
  }
}