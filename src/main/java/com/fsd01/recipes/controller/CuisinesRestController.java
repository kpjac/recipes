package com.fsd01.recipes.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fsd01.recipes.model.Cuisine;
import com.fsd01.recipes.model.CuisineItem;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cuisines")
public class CuisinesRestController {

    @GetMapping
    public List<CuisineItem> cuisineItems(@RequestParam(value = "q", required = false) String query) {
        if (StringUtils.isEmpty(query)) {
            return Arrays.stream(Cuisine.values())
                    .limit(15)
                    .map(this::mapToCuisineItem)
                    .collect(Collectors.toList());
        }

        return Arrays.stream(Cuisine.values())
                .filter(cuisine -> cuisine.getLabel()
                        .toLowerCase()
                        .contains(query))
                .limit(15)
                .map(this::mapToCuisineItem)
                .collect(Collectors.toList());
    }

    private CuisineItem mapToCuisineItem(Cuisine cuisine) {
        return CuisineItem.builder()
                .id(cuisine)
                .text(cuisine.getLabel())
                .slug(cuisine.name())
                .build();
    }
}
