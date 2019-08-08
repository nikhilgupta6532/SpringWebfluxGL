package com.springReactive.practice.designpattern.observer.interfaceImpl;

import com.springReactive.practice.designpattern.observer.Observer;
import com.springReactive.practice.designpattern.observer.subject;

import java.util.ArrayList;
import java.util.List;

public class Feed implements subject {
    private final List<Observer> observers =new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        observers.forEach(o->o.notify(tweet));
    }
}
