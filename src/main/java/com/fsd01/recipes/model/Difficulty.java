package com.fsd01.recipes.model;

import lombok.Getter;

public enum Difficulty {
    EASY("Easy"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    @Getter
    String label;

    Difficulty(String label) {
        this.label = label;
    }
}
