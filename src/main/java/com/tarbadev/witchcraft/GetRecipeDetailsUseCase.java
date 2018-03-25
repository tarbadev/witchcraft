package com.tarbadev.witchcraft;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.parseDouble;

@Component
public class GetRecipeDetailsUseCase {
    private static final List<String> UNITS = Arrays.asList(
            "lb",
            "oz",
            "tsp",
            "tbsp"
    );

    public Recipe execute(String url) {
        Document html = getRecipeHtml(url);
        String name = getRecipeNameFromHtml(html);
        List<Ingredient> ingredients = getIngredientsFromHtml(html);

        return Recipe.builder()
                .name(name)
                .url(url)
                .ingredients(ingredients)
                .build();
    }

    private Document getRecipeHtml(String recipeUrl) {
        Document recipeHtml = null;

        try {
            recipeHtml = Jsoup.connect(recipeUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeHtml;
    }

    private String getRecipeNameFromHtml(Document html) {
        Elements title = html.select("h1.entry-title");
        return title.text();
    }

    private List<Ingredient> getIngredientsFromHtml(Document html) {
        List<Ingredient> ingredients = new ArrayList<>();

        Elements htmlIngredients = html.select("div.recipe-ingredients-wrap ul li");
        for (Element htmlIngredient : htmlIngredients) {
            Ingredient ingredient = getIngredientFromText(htmlIngredient.text());
            Optional<Ingredient> maybeIngredient = ingredients.stream()
                    .filter(i ->
                            i.getName().equals(ingredient.getName()) &&
                            i.getUnit().equals(ingredient.getUnit())
                    ).findFirst();

            if (maybeIngredient.isPresent())
                maybeIngredient.get().addQuantity(ingredient.getQuantity());
            else
                ingredients.add(ingredient);
        }

        return ingredients;
    }

    private Ingredient getIngredientFromText(String text) {
        Double quantity = 0.0;
        String unit = "";
        String name = "";
        String[] words = text.split(" ");

        System.out.println("text = " + text);

        boolean quantityFound = false;

        for (String word : words) {
            if (!quantityFound) {
                Double tempQuantity = null;
                try {
                    tempQuantity = parseDouble(word);
                } catch (NumberFormatException e) {}

                if (tempQuantity != null)
                    quantity += tempQuantity;
                else if (word.contains("/")) {
                    String[] fraction = word.split("/");
                    tempQuantity = parseDouble(fraction[0]) / parseDouble(fraction[1]);
                    System.out.println("tempQuantity = " + tempQuantity);
                    quantity += tempQuantity;
                }
                else
                    quantityFound = true;
            }

            if (quantityFound) {
                String tempUnit = word
                        .toLowerCase()
                        .replaceAll("\\.", "");
                if (UNITS.contains(tempUnit))
                {
                    unit = tempUnit;
                    System.out.println("unit = " + unit);
                }
                else {
                    name = text.substring(text.indexOf(word));
                    break;
                }
            }
        }

        return Ingredient.builder()
                .name(name)
                .quantity(quantity)
                .unit(unit)
                .build();
    }
}
