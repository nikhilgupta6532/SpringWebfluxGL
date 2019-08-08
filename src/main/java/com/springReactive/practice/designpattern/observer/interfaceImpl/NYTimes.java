package com.springReactive.practice.designpattern.observer.interfaceImpl;

import com.springReactive.practice.designpattern.observer.Observer;

public class NYTimes implements Observer {
    @Override
    public void notify(String tweet) {
        if(tweet!=null && tweet.contains("money")) {
            System.out.println("Breaking news in NY!"+tweet);
        }
    }
}
