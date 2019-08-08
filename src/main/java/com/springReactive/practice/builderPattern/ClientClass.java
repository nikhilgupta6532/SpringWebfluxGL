package com.springReactive.practice.builderPattern;

public class ClientClass {

    NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(41).fat(89).build();
}
