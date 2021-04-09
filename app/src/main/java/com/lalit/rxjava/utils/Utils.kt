package com.lalit.rxjava.utils

import com.lalit.rxjava.model.ApiUser
import com.lalit.rxjava.model.User

object Utils {

    fun convertApiUserToUser(users : List<ApiUser>) : List<User>{
        val userList = ArrayList<User>()
        for (apiUser in users){
            var user = User(apiUser.id, apiUser.name, apiUser.company, false)
            userList.add(user)
        }
        return userList
    }


    fun getApiUserList() : List<ApiUser>{
        var usersList = ArrayList<ApiUser>()
        var user1 = ApiUser(1, "Lalit kumar", "Trangile")
        usersList.add(user1)
        var user2 = ApiUser(2, "Navneet kumar", "Trangile")
        usersList.add(user2)
        var user3 = ApiUser(3, "Lalit kumar", "Vinculum")
        usersList.add(user3)
        return usersList
    }

    fun getVinculumEmployees() : List<ApiUser>{
        var usersList = ArrayList<ApiUser>()
        var user1 = ApiUser(1, "Lalit kumar", "Vinculum")
        usersList.add(user1)
        var user2 = ApiUser(2, "Navneet kumar", "Vinculum")
        usersList.add(user2)
        var user3 = ApiUser(3, "ABC kumar", "Vinculum")
        usersList.add(user3)
        return usersList
    }

    fun getTrangileEmployees() : List<ApiUser>{
        var usersList = ArrayList<ApiUser>()
        var user1 = ApiUser(1, "Lalit kumar", "Vinculum")
        usersList.add(user1)
        var user2 = ApiUser(2, "Parvendra kumar", "Trangile")
        usersList.add(user2)
        var user3 = ApiUser(3, "XYZ kumar", "Trangile")
        usersList.add(user3)
        return usersList
    }

    fun getVinAndTrangileEmployess(vinList : List<ApiUser>, tsList : List<ApiUser>) : List<ApiUser>{
        var vinAndTspList = ArrayList<ApiUser>()
        for (vins in vinList){
            if (tsList.contains(vins)){
                vinAndTspList.add(vins)
            }
        }
        return vinAndTspList
    }


}