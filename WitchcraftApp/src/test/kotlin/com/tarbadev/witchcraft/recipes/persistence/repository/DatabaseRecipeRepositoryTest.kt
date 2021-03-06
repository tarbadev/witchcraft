package com.tarbadev.witchcraft.recipes.persistence.repository

import com.tarbadev.witchcraft.converter.liter
import com.tarbadev.witchcraft.converter.unit
import com.tarbadev.witchcraft.recipes.domain.entity.Ingredient
import com.tarbadev.witchcraft.recipes.domain.entity.Recipe
import com.tarbadev.witchcraft.recipes.domain.entity.Step
import com.tarbadev.witchcraft.recipes.domain.entity.StepNote
import com.tarbadev.witchcraft.recipes.persistence.entity.IngredientEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.StepEntity
import com.tarbadev.witchcraft.recipes.persistence.entity.StepNoteEntity
import org.hibernate.validator.internal.util.CollectionHelper.asSet
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Collections.emptyList

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseRecipeRepositoryTest(
    @Autowired private val recipeEntityRepository: RecipeEntityRepository,
    @Autowired private val entityManager: TestEntityManager
) {
  private lateinit var databaseRecipeRepository: DatabaseRecipeRepository

  @BeforeEach
  fun setUp() {
    recipeEntityRepository.deleteAll()

    databaseRecipeRepository = DatabaseRecipeRepository(recipeEntityRepository)
  }

  @Test
  fun saveRecipe() {
    val recipeUrl = "URL"

    val recipe = databaseRecipeRepository.save(
        Recipe(
            name = "Lasagna",
            originUrl = recipeUrl,
            ingredients = emptyList(),
            steps = listOf(Step(name = "Add pasta", note = StepNote(comment = "Some note comment")))
        )
    )
    val savedStep = recipe.steps[0]
    val expectedRecipe = Recipe(
        id = recipe.id,
        name = "lasagna",
        originUrl = recipeUrl,
        ingredients = emptyList(),
        steps = listOf(Step(
            id = savedStep.id,
            name = "Add pasta",
            note = StepNote(id = savedStep.note.id, comment = "Some note comment")))
    )

    assertEquals(expectedRecipe, recipe)
  }

  @Test
  fun saveRecipe_savesIngredients() {
    val recipe = Recipe(
        name = "Lasagna",
        ingredients = listOf(
            Ingredient(quantity = 1.unit),
            Ingredient(quantity = 1.unit)
        ),
        steps = emptyList()
    )

    val returnedRecipe = databaseRecipeRepository.save(recipe)

    val expectedRecipe = Recipe(
        id = returnedRecipe.id,
        name = "lasagna",
        ingredients = listOf(
            Ingredient(id = returnedRecipe.ingredients[0].id, quantity = 1.unit),
            Ingredient(id = returnedRecipe.ingredients[1].id, quantity = 1.unit)
        ),
        steps = emptyList()
    )

    assertEquals(expectedRecipe, returnedRecipe)
  }

  @Test
  fun updateRecipe_updatesRecipe() {
    var recipe = databaseRecipeRepository.save(
        Recipe(
            name = "Name uncorrect",
            originUrl = "URL",
            ingredients = emptyList(),
            steps = emptyList()
        )
    )

    entityManager.clear()

    val modifiedRecipe = Recipe(
        id = recipe.id,
        name = "fixed name",
        originUrl = recipe.originUrl,
        imgUrl = recipe.imgUrl,
        ingredients = recipe.ingredients,
        steps = recipe.steps
    )

    recipe = databaseRecipeRepository.update(modifiedRecipe)

    assertEquals(modifiedRecipe.name, recipe.name)
  }

  @Test
  fun findAll() {
    val url1 = "URL1"
    val url2 = "URL2"


    val expectedRecipes = listOf(
        entityManager.persist(RecipeEntity(
            name = "Lasagna",
            ingredients = emptyList(),
            originUrl = url1,
            imgUrl = "imgUrl1"
        )),
        entityManager.persist(RecipeEntity(
            name = "Tartiflette",
            ingredients = emptyList(),
            originUrl = url2,
            imgUrl = "imgUrl2"
        ))
    )

    entityManager.flush()
    entityManager.clear()

    assertEquals(expectedRecipes.size, databaseRecipeRepository.findAll().size)
  }

  @Test
  fun findAll_returnsRecipeOrderedByName() {
    val tartiflette = toDomain(
        entityManager.persist(RecipeEntity(
            name = "Tartiflette",
            ingredients = emptyList(),
            steps = emptySet()
        ))
    )
    val pizza = toDomain(
        entityManager.persist(RecipeEntity(
            name = "Pizza",
            ingredients = emptyList(),
            steps = emptySet()
        ))
    )
    val burger = toDomain(
        entityManager.persistAndFlush(RecipeEntity(
            name = "Burger",
            ingredients = emptyList(),
            steps = emptySet()
        ))
    )

    entityManager.clear()

    val expectedRecipes = listOf(burger, pizza, tartiflette)

    val recipes = databaseRecipeRepository.findAll()
    assertEquals(expectedRecipes.size, recipes.size)
    assertEquals(burger.name, recipes[0].name)
    assertEquals(pizza.name, recipes[1].name)
    assertEquals(tartiflette.name, recipes[2].name)
  }

  @Test
  fun findAll_returnsOnlyNonArchivedRecipes() {
    toDomain(
        entityManager.persist(RecipeEntity(
            name = "Tartiflette",
            isArchived = true
        ))
    )
    val pizza = toDomain(
        entityManager.persist(RecipeEntity(
            name = "Pizza",
            isArchived = false
        ))
    )
    toDomain(
        entityManager.persistAndFlush(RecipeEntity(
            name = "Burger",
            isArchived = true
        ))
    )

    entityManager.clear()

    val expectedRecipes = listOf(pizza)

    val recipes = databaseRecipeRepository.findAll()
    assertEquals(expectedRecipes, recipes)
  }

  @Test
  fun findById() {
    val recipe = toDomain(
        entityManager.persistAndFlush(RecipeEntity(
            name = "Recipe 1",
            ingredients = emptyList(),
            steps = asSet(StepEntity(name = "First step", notes = listOf(StepNoteEntity(comment = "Some comment")))),
            originUrl = "URL"
        ))
    )

    entityManager.clear()

    assertEquals(recipe, databaseRecipeRepository.findById(recipe.id))
  }

  @Test
  fun findById_returnsIngredientsOrderedByName() {
    val recipe = entityManager.persistAndFlush(
        RecipeEntity(
            name = "Recipe 1",
            ingredients = listOf(
                IngredientEntity(name = "Parsley"),
                IngredientEntity(name = "Cilantro"),
                IngredientEntity(name = "Egg")
            ),
            steps = emptySet(),
            originUrl = "URL"
        )
    ).toRecipe()

    entityManager.clear()

    assertEquals(
        recipe.copy(ingredients = recipe.ingredients.sortedBy { it.name }),
        databaseRecipeRepository.findById(recipe.id)
    )
  }

  @Test
  fun findById_returnsNullWhenRecipeIsArchived() {
    val recipe = entityManager.persistAndFlush(
        RecipeEntity(
            name = "Recipe 1",
            isArchived = true
        )
    ).toRecipe()

    entityManager.clear()

    assertNull(databaseRecipeRepository.findById(recipe.id))
  }

  @Test
  fun delete() {
    val recipeEntity = entityManager.persistAndFlush(RecipeEntity(name = "Lasagna"))

    entityManager.clear()

    assertFalse(entityManager.find(RecipeEntity::class.java, recipeEntity.id)!!.isArchived)

    databaseRecipeRepository.delete(recipeEntity.id)

    entityManager.clear()

    assertTrue(entityManager.find(RecipeEntity::class.java, recipeEntity.id)!!.isArchived)
  }

  @Test
  fun setFavorite() {
    val recipe = entityManager.persistAndFlush(RecipeEntity(name = "Lasagna"))
    entityManager.clear()

    assertFalse(databaseRecipeRepository.findById(recipe.id)!!.favorite)

    databaseRecipeRepository.setFavorite(recipe.id, true)
    entityManager.clear()

    assertTrue(databaseRecipeRepository.findById(recipe.id)!!.favorite)
  }

  @Test
  fun findAllFavorite() {
    val recipes = listOf(
        entityManager.persist(RecipeEntity(favorite = true)),
        entityManager.persist(RecipeEntity(favorite = true)),
        entityManager.persist(RecipeEntity(favorite = false)),
        entityManager.persist(RecipeEntity(favorite = true)),
        entityManager.persist(RecipeEntity(favorite = true)),
        entityManager.persist(RecipeEntity(favorite = false)),
        entityManager.persist(RecipeEntity(favorite = true)),
        entityManager.persist(RecipeEntity(favorite = false))
    )
        .filter { it.favorite }
        .map { it.toRecipe() }
        .sortedBy { it.id }

    entityManager.flush()
    entityManager.clear()

    assertEquals(recipes, databaseRecipeRepository.findAllFavorite().sortedBy { it.id })
  }

  @Test
  fun findAllFavorite_returnsNonArchivedRecipes() {
    val recipes = listOf(
        entityManager.persist(RecipeEntity(favorite = true, isArchived = false)),
        entityManager.persist(RecipeEntity(favorite = true, isArchived = false)),
        entityManager.persist(RecipeEntity(favorite = false, isArchived = false)),
        entityManager.persist(RecipeEntity(favorite = true, isArchived = true)),
        entityManager.persist(RecipeEntity(favorite = true, isArchived = true)),
        entityManager.persist(RecipeEntity(favorite = false, isArchived = false)),
        entityManager.persist(RecipeEntity(favorite = true, isArchived = false)),
        entityManager.persist(RecipeEntity(favorite = false, isArchived = false))
    )
        .filter { it.favorite && !it.isArchived }
        .map { it.toRecipe() }
        .sortedBy { it.id }

    entityManager.flush()
    entityManager.clear()

    assertEquals(recipes, databaseRecipeRepository.findAllFavorite().sortedBy { it.id })
  }

  @Test
  fun findLastAddedRecipes() {
    val recipes = listOf(
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet())),
        entityManager.persist(RecipeEntity(name = "", ingredients = emptyList(), steps = emptySet()))
    )
        .map { it.toRecipe() }
        .sortedBy { it.id }
        .reversed()
        .subList(0, 8)

    entityManager.flush()
    entityManager.clear()

    assertEquals(recipes, databaseRecipeRepository.findLastAddedRecipes())
  }

  @Test
  fun findLastAddedRecipes_returnsNonArchivedRecipes() {
    val recipes = listOf(
        entityManager.persist(RecipeEntity(isArchived = true)),
        entityManager.persist(RecipeEntity(isArchived = false)),
        entityManager.persist(RecipeEntity(isArchived = false)),
        entityManager.persist(RecipeEntity(isArchived = false))
    )

    entityManager.flush()
    entityManager.clear()

    assertEquals(recipes.size - 1, databaseRecipeRepository.findLastAddedRecipes().size)
  }

  @Test
  fun exists_returnsTrueWhenRecipeExists() {
    val recipeEntity = entityManager.persistAndFlush(
        RecipeEntity()
    )

    assertTrue(databaseRecipeRepository.existsById(recipeEntity.id))
  }

  @Test
  fun exists_returnsFalseWhenRecipeDoesNotExist() {
    assertFalse(databaseRecipeRepository.existsById(32))
  }

  private fun toDomain(recipeEntity: RecipeEntity) =
      Recipe(
          id = recipeEntity.id,
          name = recipeEntity.name.toLowerCase(),
          originUrl = recipeEntity.originUrl,
          imgUrl = recipeEntity.imgUrl,
          ingredients = recipeEntity.ingredients
              .map { ingredientEntity ->
                Ingredient(
                    id = ingredientEntity.id,
                    name = ingredientEntity.name,
                    quantity = ingredientEntity.quantity.liter
                )
              },
          steps = recipeEntity.steps
              .map { stepEntity ->
                Step(
                    id = stepEntity.id,
                    name = stepEntity.name,
                    note = stepEntity.notes.elementAtOrNull(0)?.toStepNote() ?: StepNote()
                )
              }
      )
}
