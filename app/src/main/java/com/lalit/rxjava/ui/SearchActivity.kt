package com.lalit.rxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lalit.rxjava.R
import com.lalit.rxjava.utils.getQueryTextChangeObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SearchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupSearchObservable()

    }

    private fun setupSearchObservable(){

        search_searchView.getQueryTextChangeObservable()
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it ->
                   if (it.isEmpty()){
                       search_text_result.text = ""
                       return@filter false
                   }else{
                       return@filter true
                   }
             }
            .distinctUntilChanged()
            .switchMap { query -> dataFromNetwork(query).doOnError {
                //handle Error
            }.onErrorReturn { "" } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> search_text_result.text = result }

    }


    /**
     * Simulation of network data
     */
    private fun dataFromNetwork(query: String): Observable<String> {
        return Observable.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map {
                query
            }
    }

}