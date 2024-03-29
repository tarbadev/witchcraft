package com.tarbadev.witchcraft.weeks.persistence

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import com.tarbadev.witchcraft.weeks.domain.entity.*
import com.tarbadev.witchcraft.weeks.persistence.entity.WeekEntity
import com.tarbadev.witchcraft.weeks.persistence.repository.DatabaseWeekRepository
import com.tarbadev.witchcraft.weeks.persistence.repository.WeekEntityRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseWeekRepositoryTest(
    @Autowired private val weekEntityRepository: WeekEntityRepository,
    @Autowired private val entityManager: TestEntityManager
) {
  private lateinit var databaseWeekRepository: DatabaseWeekRepository

  @BeforeEach
  fun setUp() {
    weekEntityRepository.deleteAll()

    databaseWeekRepository = DatabaseWeekRepository(weekEntityRepository)
  }

  @Test
  fun findByYearAndWeekNumber() {
    val week = entityManager.persistAndFlush(
        WeekEntity(
            days = emptyList(),
            year = 2018,
            weekNumber = 24
        )
    ).week()

    entityManager.clear()

    assertEquals(week, databaseWeekRepository.findByYearAndWeekNumber(2018, 24))
  }

  @Test
  fun save() {
    assertEquals(0, weekEntityRepository.count())

    databaseWeekRepository.save(Week(days = emptyList()))

    assertEquals(1, weekEntityRepository.count())
  }

  @Test
  fun save_updatesWeek() {
    val lasagnaEntity = entityManager.persist(
        RecipeEntity(
            name = "lasagna",
            originUrl = "http://origin",
            imgUrl = "http://imgurl"
        )
    )
    val tartifletteEntity = entityManager.persist(
        RecipeEntity(
            name = "tartiflette",
            originUrl = "http://origin",
            imgUrl = "http://imgurl"
        )
    )
    val racletteEntity = entityManager.persistAndFlush(
        RecipeEntity(
            name = "raclette",
            originUrl = "http://origin",
            imgUrl = "http://imgurl"
        )
    )
    entityManager.clear()

    val lasagna = Recipe(
        id = lasagnaEntity.id,
        originUrl = "http://origin",
        imgUrl = "http://imgurl",
        name = "lasagna",
        ingredients = emptyList(),
        steps = emptyList()
    )
    val tartiflette = Recipe(
        id = tartifletteEntity.id,
        originUrl = "http://origin",
        imgUrl = "http://imgurl",
        name = "tartiflette",
        ingredients = emptyList(),
        steps = emptyList()
    )
    val raclette = Recipe(
        id = racletteEntity.id,
        originUrl = "http://origin",
        imgUrl = "http://imgurl",
        name = "raclette",
        ingredients = emptyList(),
        steps = emptyList()
    )

    val week = Week(
        year = 2018,
        weekNumber = 12,
        days = listOf(
            Day(
                name = DayName.MONDAY,
                meals = listOf(Meal(recipe = lasagna, mealType = MealType.LUNCH))
            ),
            Day(
                name = DayName.THURSDAY,
                meals = listOf(Meal(recipe = tartiflette, mealType = MealType.DINER))
            )
        ))

    databaseWeekRepository.save(week)
    entityManager.clear()

    val returnedWeek = databaseWeekRepository.findByYearAndWeekNumber(week.year, week.weekNumber)
    val weekDomain = Week(
        id = returnedWeek!!.id,
        year = 2018,
        weekNumber = 12,
        days = listOf(
            Day(
                id = returnedWeek.days[0].id,
                name = DayName.MONDAY,
                meals = listOf(Meal(id = returnedWeek.days[0].meals[0].id, recipe = lasagna, mealType = MealType.LUNCH))
            ),
            Day(
                id = returnedWeek.days[1].id,
                name = DayName.THURSDAY,
                meals = listOf(Meal(id = returnedWeek.days[1].meals[0].id, recipe = tartiflette, mealType = MealType.DINER))
            )
        )
    )

    assertEquals(weekDomain, returnedWeek)

    val updatedWeek = Week(
        id = returnedWeek.id,
        year = 2018,
        weekNumber = 12,
        days = listOf(
            Day(
                id = returnedWeek.days[0].id,
                name = DayName.MONDAY,
                meals = listOf(Meal(id = returnedWeek.days[0].meals[0].id, recipe = lasagna, mealType = MealType.LUNCH))
            ),
            Day(
                name = DayName.WEDNESDAY,
                meals = listOf(Meal(recipe = raclette, mealType = MealType.DINER))
            )
        )
    )

    databaseWeekRepository.save(updatedWeek)
    entityManager.clear()

    val returnedUpdatedWeek = databaseWeekRepository.findByYearAndWeekNumber(updatedWeek.year, updatedWeek.weekNumber)
    val updatedWeekDomain = Week(
        id = returnedWeek.id,
        year = 2018,
        weekNumber = 12,
        days = listOf(
            Day(
                id = returnedWeek.days[0].id,
                name = DayName.MONDAY,
                meals = listOf(Meal(id = returnedWeek.days[0].meals[0].id, recipe = lasagna, mealType = MealType.LUNCH))
            ),
            Day(
                id = returnedUpdatedWeek!!.days[1].id,
                name = DayName.WEDNESDAY,
                meals = listOf(Meal(id = returnedUpdatedWeek.days[1].meals[0].id, recipe = raclette, mealType = MealType.DINER))
            )
        )
    )

    assertEquals(updatedWeekDomain, returnedUpdatedWeek)
  }
}