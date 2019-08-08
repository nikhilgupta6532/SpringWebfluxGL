package com.springReactive.practice.designpattern.observer.interfaceImpl;

import com.springReactive.practice.designpattern.observer.Observer;

public class LeMonde implements Observer {
    @Override
    public void notify(String tweet) {
        if(tweet!=null && tweet.contains("wine")) {
            System.out.println("Today cheese, wine and news!"+tweet);
        }
    }
}
