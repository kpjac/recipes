package com.fsd01.recipes.model;

import lombok.Getter;

public enum Cuisine {
    ALBANIAN("Albanian"),
    AMERICAN("American"),
    ARAB("Arab"),
    ARGENTINE("Argentine"),
    ARMENIAN("Armenian"),
    ASSYRIAN("Assyrian"),
    AZERBAIJANI("Azerbaijani"),
    BELARUSIAN("Belarusian"),
    BENGALI("Bengali"),
    BRAZILIAN("Brazilian"),
    BRITISH("British"),
    BULGARIAN("Bulgarian"),
    CAJUN("Cajun"),
    CAMBODIAN("Cambodian"),
    CANTONESE("Cantonese"),
    CARIBBEAN("Caribbean"),
    CHECHEN("Chechen"),
    CHINESE("Chinese"),
    CIRCASSIAN("Circassian"),
    DANISH("Danish"),
    EGYPTIAN("Egyptian"),
    ENGLISH("English"),
    ETHIOPIAN("Ethiopian"),
    FILIPINO("Filipino"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    INDIAN("Indian"),
    INDONESIAN("Indonesian"),
    IRISH("Irish"),
    ITALIAN("Italian"),
    JAMAICAN("Jamaican"),
    JAPANESE("Japanese"),
    ISRAELI("Israeli"),
    KOREAN("Korean"),
    KURDISH("Kurdish"),
    LATVIAN("Latvian"),
    LEBANESE("Lebanese"),
    LITHUANIAN("Lithuanian"),
    MALAY("Malay"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    NATIVE_AMERICAN("Native American"),
    NEPALESE("Nepalese"),
    PAKISTANI("Pakistani"),
    PASHTUN("Pashtun"),
    PERSIAN("Persian"),
    PERUVIAN("Peruvian"),
    POLISH("Polish"),
    PORTUGUESE("Portuguese"),
    QUEBECOIS("Québécois"),
    ROMANIAN("Romanian"),
    RUSSIAN("Russian"),
    SCANDINAVIAN("Scandinavian"),
    SLOVAK("Slovak"),
    SLOVENIAN("Slovenian"),
    SOMALI("Somali"),
    SOUTH_INDIAN("South Indian"),
    SPANISH("Spanish"),
    TAIWANESE("Taiwanese"),
    THAI("Thai"),
    TURKISH("Turkish"),
    UYGHUR("Uyghur"),
    VIETNAMESE("Vietnamese");

    @Getter
    String label;

    Cuisine(String label) {
        this.label = label;
    }

}
