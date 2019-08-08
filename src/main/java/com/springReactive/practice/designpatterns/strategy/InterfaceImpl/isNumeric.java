package com.springReactive.practice.designpatterns.strategy.InterfaceImpl;

import com.springReactive.practice.designpatterns.strategy.ValidationStrategy;

public class isNumeric implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
        return s.matches("\\d+");
    }
}
