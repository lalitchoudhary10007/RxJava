package com.lalit.rxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lalit.rxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // doWork()
        fetchUsers()

    }


    private fun doWork() {

        getObservable()
            .subscribeOn(Schedulers.io())
            .delay(
                2,
                TimeUnit.SECONDS
            ) // to understand if we don't observer on main thread it will return response on same thread on which we subscribed
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(createObserver())

    }

    private fun fetchUsers(){
        createObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<String>>{
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
                }

                override fun onNext(t: List<String>?) {
                    Log.d(TAG, "onNext: $t")
                    main_text_txt.append("\n onNext:- $t")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                    main_text_txt.append("\n onComplete")
                }

            })

    }

    private fun createObservable(): @NonNull Observable<List<String>> {
        return Observable.create { emitter ->
              val users = mutableListOf("one", "two", "three", "four") //here we will execute our DB query or anything else like fileIO
              emitter.onNext(users)
              emitter.onComplete()
        }
    }


    private fun getObservable(): @NonNull Observable<String> {
        return Observable.just("Cricket", "Football")
    }

    private fun createObserver(): Observer<String> {
        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
            }

            override fun onNext(t: String?) {
                Log.d(TAG, "onNext: $t")
                main_text_txt.append("\n onNext:- $t")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError: $e")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
                main_text_txt.append("\n onComplete")
            }
        }
        return observer
    }


}