package com.tarbadev.witchcraft.web;

import lombok.Value;

import java.util.List;

@Value
public class NewCartForm {
    List<Integer> checkedRecipeIds;
}
