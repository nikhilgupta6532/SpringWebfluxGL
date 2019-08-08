package com.springReactive.practice.designpattern.observer;

public interface subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
