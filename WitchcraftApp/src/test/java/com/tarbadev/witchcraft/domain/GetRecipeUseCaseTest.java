package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.TestResources;
import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import com.tarbadev.witchcraft.domain.usecase.GetRecipeUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GetRecipeUseCaseTest {
    private GetRecipeUseCase subject;

    @Autowired private TestResources testResources;
    @Mock private RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        subject = new GetRecipeUseCase(recipeRepository);
    }

    @Test
    public void execute() {
        Recipe recipe = testResources.getRecipe();

        Integer recipeId = 123;
        given(recipeRepository.findById(recipeId)).willReturn(recipe);

        assertThat(subject.execute(recipeId)).isEqualTo(recipe);
    }
}