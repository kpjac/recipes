package com.fsd01.recipes.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CuisineItem {

    private Cuisine id;
    private String text;
    private String slug;

}
