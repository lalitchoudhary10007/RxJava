package com.lalit.rxjava.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lalit.rxjava.R
import com.lalit.rxjava.model.ApiUser
import com.lalit.rxjava.model.UserDetail
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_flatmap_operator.*
import kotlinx.android.synthetic.main.activity_map_operator.*

class FlatmapOperatorActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FlatmapOperatorActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flatmap_operator)

        getUserAndDetails()

    }


    private fun getUserAndDetails() {
        getUserList().flatMap { it ->
            return@flatMap Observable.fromIterable(it)
        }.flatMap { it ->
            return@flatMap getUserDetails(it.id)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
          //  .toList() //method is used for getuserdetails as a list if we don;t use then we will get user details one by one
          //it will return as a list in SingleObserver
            .subscribe(object : Observer<UserDetail> {
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe: ${d?.isDisposed}")
                    flatmap_text_result.append("\n onSubscribe")
                }

                override fun onNext(t: UserDetail) {
                    Log.d(TAG, "onNext: $t")
                    flatmap_text_result.append("\n UserDetails:- ${t.salary + " " + t.address}")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError: $e")
                    flatmap_text_result.append("\n onError")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                    flatmap_text_result.append("\n onComplete")
                }

            })
    }


    private fun getUserDetails(id: Int): Observable<UserDetail> {
        return Observable.create { e ->
            e.onNext(getDetailOfUser(id))
            e.onComplete()
        }
    }

    private fun getUserList(): Observable<List<ApiUser>> {
        return Observable.create { e ->
            e.onNext(addUsersList())
            e.onComplete()
        }
    }


    //this is our api call which is returning 5 users list
    private fun addUsersList(): List<ApiUser> {
        val users = ArrayList<ApiUser>()
        val user1 = ApiUser(1, "first", "A")
        val user2 = ApiUser(2, "second", "B")
        val user3 = ApiUser(3, "third", "C")
        val user4 = ApiUser(4, "fourth", "D")
        val user5 = ApiUser(5, "five", "E")
        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)
        users.add(user5)
        return users
    }

    private fun addUserDetails(): List<UserDetail> {
        val userDetails = ArrayList<UserDetail>()
        val userDetail1 = UserDetail(1, "20K", "Gurgaon1")
        val userDetail2 = UserDetail(2, "30K", "Gurgaon2")
        val userDetail3 = UserDetail(3, "40K", "Gurgaon3")
        val userDetail4 = UserDetail(4, "50K", "Gurgaon4")
        val userDetail5 = UserDetail(5, "60K", "Gurgaon5")
        userDetails.add(userDetail1)
        userDetails.add(userDetail2)
        userDetails.add(userDetail3)
        userDetails.add(userDetail4)
        userDetails.add(userDetail5)
        return userDetails
    }

    private fun getDetailOfUser(id: Int): UserDetail {
        return addUserDetails().find { it.id == id }!!
    }

}