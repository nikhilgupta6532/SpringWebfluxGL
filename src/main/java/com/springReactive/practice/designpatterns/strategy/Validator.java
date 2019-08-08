package com.springReactive.practice.designpatterns.strategy;

public class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(String s) {
        return strategy.execute(s);
    }
}

class createInstance {
    Validator lowerCaseValidator = new Validator((String s)->s.matches("[a-z]+"));
    boolean b1 = lowerCaseValidator.validate("bbb");

    Validator numericValidator = new Validator((String s)->s.matches("\\d+"));
    boolean b2 = numericValidator.validate("123");

}

