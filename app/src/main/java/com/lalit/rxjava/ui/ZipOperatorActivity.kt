package com.lalit.rxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lalit.rxjava.R
import com.lalit.rxjava.model.ApiUser
import com.lalit.rxjava.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_zip_operator.*

class ZipOperatorActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ZipOperatorActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zip_operator)

        getEmpsOfBoth()

    }

    private fun getEmpsOfBoth(){
        Observable.zip(getTspEmps(), getVinEmps(), BiFunction { t1, t2 ->
            return@BiFunction Utils.getVinAndTrangileEmployess(t1, t2)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<ApiUser>>{
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
                    zip_text_result.append("\n onSubscribe")
                }

                override fun onNext(t: List<ApiUser>?) {
                    Log.d(TAG, "onNext:- $t")
                    for (user in t!!){
                        zip_text_result.append("\n User is in Both:- ${user.name}")
                    }
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError:- $e")
                    zip_text_result.append("\n onError")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                    zip_text_result.append("\n onComplete")
                }

            })
    }


    private fun getVinEmps(): Observable<List<ApiUser>> {
        return Observable.create { e ->
            e.onNext(Utils.getVinculumEmployees())
            e.onComplete()
        }
    }

    private fun getTspEmps(): Observable<List<ApiUser>> {
        return Observable.create { e ->
            e.onNext(Utils.getTrangileEmployees())
            e.onComplete()
        }
    }


}