package com.example.demo

import com.example.demo.model.MessageBean
import com.example.demo.model.UserBean
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource

class MessageDao {
    companion object {
        //Typage du Dao <Bean, typage Id>
        private fun getDao(jdbc: JdbcConnectionSource): Dao<MessageBean, Long> =
                DaoManager.createDao(jdbc, MessageBean::class.java)

        //Méthode pour sauvegarder un message
        fun saveMessage(data: MessageBean, jdbc: JdbcConnectionSource) {
            getDao(jdbc).createOrUpdate(data)
        }

//        getDao(jdbc).queryBuilder().where().eq("user_pseudo", name).query()

        //Méthode pour charger tous les message en Arraylist
        fun loadMessages(jdbc: JdbcConnectionSource): ArrayList<MessageBean> {
            var array = ArrayList(getDao(jdbc).queryBuilder().orderBy("post_date", false).limit(20).query())
            array.map { it.user?.user_session_id = null; it.user?.user_mdp = null; it.user?.user_id = null; it.id_message = null; }
            println(array)
            return array
        }
    }
}