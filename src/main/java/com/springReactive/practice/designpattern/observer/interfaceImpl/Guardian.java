package com.springReactive.practice.designpattern.observer.interfaceImpl;

import com.springReactive.practice.designpattern.observer.Observer;

public class Guardian implements Observer {
    @Override
    public void notify(String tweet) {
        if(tweet!=null && tweet.contains("queen")) {
            System.out.println("Yet another news in london"+tweet);
        }
    }
}
