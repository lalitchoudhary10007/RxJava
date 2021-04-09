package com.lalit.rxjava.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lalit.rxjava.R
import com.lalit.rxjava.model.ApiUser
import com.lalit.rxjava.model.User
import com.lalit.rxjava.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map_operator.*

class MapOperatorActivity : AppCompatActivity() {

   companion object{
       const val TAG = "MapOperatorActivity"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_operator)

        getUsers()

    }


    private fun getUsers(){
        getObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> return@map Utils.convertApiUserToUser(response) }
            .subscribe(object : Observer<List<User>>{
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
                    map_text_result.append("\n onSubscribe")
                }

                override fun onNext(t: List<User>) {
                    Log.d(TAG, "onNext: $t")
                    for (user in t){
                        map_text_result.append("\n User:- ${user.name}")
                    }
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: $e")
                    map_text_result.append("\n onError:- $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                    map_text_result.append("\n onComplete")
                }

            })

    }

    private fun getObservable() : Observable<List<ApiUser>> {
        return Observable.create { e ->
            e.onNext(Utils.getApiUserList())
            e.onComplete()
        }
    }




}