package com.springReactive.practice.subscriber;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("subscribed");
        request(7);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value);
        request(7);
    }
}
