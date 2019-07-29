package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StepEntityRepository : JpaRepository<StepEntity, Int>