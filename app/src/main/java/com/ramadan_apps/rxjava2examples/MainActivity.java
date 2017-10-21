package com.ramadan_apps.rxjava2examples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private Observable createObserableUsingJust(){
        return Observable.just(1,2,13,43,50);
    }

    private Observable createObserableUsingForm(){
        return Observable.fromArray(new Integer[]{10,20,30,40,50});
    }

    private Observable createObservableUsingInterval(){
        return Observable.interval(0,2, TimeUnit.SECONDS);
    }

    private Observable filter(){
        return Observable.just(1,2,3,4,5,6,7,8)
                  .filter(new Predicate<Integer>() {
                      @Override
                      public boolean test(Integer num) throws Exception {
                          return num%2==0;
                      }
                  });
    }

    private Observable showFunctionOfSkip(){
        return Observable.fromArray(new Integer[]{1,2,3,4,5,6,7,8})
                  .skip(2);
    }

    private Observable showFunctionOfTake(){
        return Observable.fromArray(new Integer[]{1,2,3,4,5,6,7,8})
                .take(2);
    }

    private Observable showFunctionOfMerge(){
        String [] myName ={"Mahmoud","Ramadan","Abd","elwahed"} ;
        String [] myJob ={"I am ","Software Engineer"};

        Observable myNameObservable= Observable.fromArray(myName);
        Observable myJobObservable= Observable.fromArray(myJob);

        return Observable.merge(myNameObservable,myJobObservable);
    }

    class CustomObject{
        int number;
        String ch;
    }

    private Observable<CustomObject> showFunctionOfZip(){

        Observable<Integer> numObservable = Observable.fromArray(new Integer[]{11, 22, 33, 44, 55});  //Emits integers
        Observable<String> chObservable = Observable.fromArray(new String[]{"S", "O", "O", "N",});  //Emits characters

        return Observable.zip(numObservable, chObservable, new BiFunction<Integer, String, CustomObject>() {
            @Override
            public CustomObject apply(Integer integer, String s) throws Exception {
                CustomObject customObject = new CustomObject();
                customObject.number=integer;
                customObject.ch = s;
                return customObject;
            }
        });

    }

    private Observable showMapFunction(){
        return Observable.just(1,2,3,4,5)
                         .map(new Function() {
                             @Override
                             public Object apply(Object ob) throws Exception {
                                 return (Integer)ob*10;
                             }
                         });

    }

    private void showFunctionOfSubscribeOn(){
        Observable.just(100, 200, 300)
                .subscribeOn(Schedulers.newThread())
                .subscribe(getObserver());
    }


    private void showFunctionOfObserverOn(){
        Observable.just(100, 200, 300)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                })
                .observeOn(Schedulers.newThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

    }


    private io.reactivex.Observer<Integer> getObserver(){
        io.reactivex.Observer<Integer> observer = new io.reactivex.Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + value + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: All Done!" + Thread.currentThread().getName());
            }
        };
        return  observer;
    }


}
