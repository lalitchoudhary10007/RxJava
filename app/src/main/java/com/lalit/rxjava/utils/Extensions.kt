package com.lalit.rxjava.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

fun SearchView.getQueryTextChangeObservable() : Observable<String>{

    val subject = PublishSubject.create<String>()

    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText)
            return true
        }
    })

    return subject

}