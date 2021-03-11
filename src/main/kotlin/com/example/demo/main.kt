package com.example.demo

import com.example.demo.model.MessageBean
import com.example.demo.model.UserBean
import com.google.gson.Gson
import com.google.gson.JsonParser

fun main() {
    val conn = DaoConnexion()
    var user = UserBean(12, "jean", 503930245636727438, "test")

//    var gson = Gson()
//    gson.toJson(json)
//    println(gson)
//    val okok = gson.fromJson<String>(json, Gson::class.java)
//    println(okok)
//    println(UserDao.pseudoAlreadyExists("jean", conn.connectionSource))
//    var message = MessageBean("salut c'est un test", user)
//    var result = UserDao.connectUser(user, conn.connectionSource)
//    MessageDao.loadMessages(conn.connectionSource)
//    var result = MessageDao.saveMessage(message, conn.connectionSource)
//    println(result.toString() + " THiS IS THE TEST")
    //Déconnexion du Dao
    conn.closeConnexion()
}