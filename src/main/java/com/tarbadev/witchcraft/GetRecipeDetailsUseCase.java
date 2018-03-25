package com.tarbadev.witchcraft;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetRecipeDetailsUseCase {
    public Recipe execute(String url) {
        Document html = getRecipeHtml(url);
        String name = getRecipeNameFromHtml(html);

        Recipe recipe = Recipe.builder()
                .name(name)
                .url(url)
                .build();
        return recipe;
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

    private String getRecipeNameFromHtml(Document recipeHtml) {
        Elements title = recipeHtml.select("h1.entry-title");
        String name = title.text();
        System.out.println("name = " + name);
        return name;
    }
}
