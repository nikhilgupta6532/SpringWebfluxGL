package com.springReactive.practice.bootstrap;

import com.springReactive.practice.MapService.AbstractMapService;
import com.springReactive.practice.model.BaseEntity;
import com.springReactive.practice.subscriber.SampleSubscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Component
public class CommandRunner implements CommandLineRunner {

    public static Mono<String> doSomething(){
        System.out.println("hello");
        System.out.println("hi");
        return Mono.just("Nikhil");
    }

  private final AbstractMapService mapService;

  public CommandRunner(AbstractMapService mapService) {
    this.mapService = mapService;
  }

  @Override
    public void run(String... args) throws Exception {


        boolean anagrams = mapService.isAnagrams("NIKHML", "IHKNLP");
        System.out.println(anagrams);

        BaseEntity b = new BaseEntity();
        b.setFirstName("Nihkil");
        b.setLastName("Gupta");
        b.setAge(23);
        b.setId(1L);

        mapService.save(b);
        System.out.println("object 1 inserted");

        BaseEntity b1 = new BaseEntity();
        b1.setFirstName("Ankit");
        b1.setLastName("Ranga");
        b1.setAge(23);
        b1.setId(2L);

        mapService.save(b1);
        System.out.println("object 2 inserted");

        Flux<Integer> range = Flux.range(1, 20);
        range.subscribe(i-> System.out.println(i),
                error-> System.out.println("Error" + error),
                ()-> System.out.println("Done"),
                subscription -> subscription.request(10));

        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> range1 = Flux.range(1, 4);
        range1.subscribe(i-> System.out.println(i),
                error-> System.out.println("Error" + error),
                ()-> System.out.println("Done"),
                subscription -> subscription.request(10));
        range1.subscribe(ss);

        Flux.range(1,10)
                .doOnRequest(r-> System.out.println("request of "+r))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("cancelling after having received "+ value);
                        cancel();
                    }
                });

        Flux.generate(()->0,
                (state,sink)->{
                    sink.next("3 *" + state + " = " + 3*state);
                    if(state==10) sink.complete();
                    return state+1;
                });


        final Mono<String> mono  = Mono.just("hello");

            new Thread(()->mono
                    .map(msg->msg+ " thread")
                    .subscribe(v-> System.out.println(v + Thread.currentThread().getName()))).run();

        Scheduler s = Schedulers.newParallel("parrallel-scheduler",4);

        final Flux<String> flux = Flux.range(1,4)
                .map(i->i+10)
                .publishOn(s)
                .map(i->"value" + i);

        new Thread(()->{
            flux.subscribe(System.out::println);
            try {
                Thread.sleep(200);
                System.out.println("hello nikhil");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).run();

          Flux.just(0, 1)
                .map(i -> 2 / i)
                .map(val -> val * 2)
                .onErrorReturn(1)
                .subscribe(val-> System.out.println(val));

          Flux.interval(Duration.ofMillis(250))
                  .map(input->{
                      if(input<3) return "tick "+ input;
                      throw new RuntimeException("boom");
                  })
                  .retry(1)
                  .elapsed()
                  .subscribe(System.out::println,System.err::println);
          Thread.sleep(5000);

          Flux.error(new IllegalArgumentException())
                  .doOnError(System.out::println)
                  .retryWhen(companion->companion.take(3))
                  .subscribe();

          Mono.just("hey").switchIfEmpty(doSomething());

         // Mono.empty().switchIfEmpty(Mono.defer(()->doSomething()));


    }
}
