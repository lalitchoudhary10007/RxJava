package com.lalit.rxjava.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lalit.rxjava.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_merge_operator.*

class MergeAndConcatOperatorActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MergeAndConcatOperator"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_merge_operator)


        MergeOperator()

    }


    private fun MergeOperator() {
        //it doesn't maintain any order so it may be provide in order or may be not
        val observable1 = Observable.fromArray("A1", "A2", "A3", "A4")
        val observable2 = Observable.fromArray("B1", "B2", "B3", "B4")

//        Observable.merge(observable1, observable2)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<String> {
//                override fun onSubscribe(d: Disposable?) {
//                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
//                    merge_text_result.append("\n onSubscribe")
//                }
//
//                override fun onNext(t: String?) {
//                    Log.d(TAG, "onNext: $t")
//                    merge_text_result.append("\n onNext:- $t")
//                }
//
//                override fun onError(e: Throwable?) {
//                    Log.d(TAG, "onError: $e")
//                    merge_text_result.append("\n onError:- $e")
//                }
//
//                override fun onComplete() {
//                    Log.d(TAG, "onComplete")
//                    merge_text_result.append("\n onComplete")
//                }
//            })

        Observable.concat(observable1, observable2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
                    merge_text_result.append("\n onSubscribe")
                }

                override fun onNext(t: String?) {
                    Log.d(TAG, "onNext: $t")
                    merge_text_result.append("\n onNext:- $t")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: $e")
                    merge_text_result.append("\n onError:- $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                    merge_text_result.append("\n onComplete")
                }
            })

    }

}