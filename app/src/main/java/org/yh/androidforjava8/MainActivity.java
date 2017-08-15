package org.yh.androidforjava8;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable()
        {
            public void run()
            {
                System.out.println("Run!");
            }
        }).start();

        new Thread(() -> System.out.println("Run!")).start();
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        findViewById(R.id.text).setOnClickListener(view ->
        {
            Toast.makeText(this, "afadfasd", Toast
                    .LENGTH_SHORT).show();
            onRunSchedulerExampleButtonClicked();
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Collections.<String>emptyList();
        disposables.clear();
    }

    void onRunSchedulerExampleButtonClicked()
    {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>()
                {
                    @Override
                    public void onComplete()
                    {
                        Log.e(TAG, "onComplete()");
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String string)
                    {
                        Log.e(TAG, "onNext(" + string + ")");
                    }
                }));
    }

    static Observable<String> sampleObservable()
    {
        return Observable.defer(() ->
        {
            // Do some long running operation
            SystemClock.sleep(5000);
            return Observable.just("one", "two", "three", "four", "five");
        });
    }

}
