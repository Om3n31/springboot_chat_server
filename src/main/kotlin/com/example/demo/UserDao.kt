package com.example.demo

import com.example.demo.model.UserBean
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.random.Random

class UserDao {
    companion object {
        //Typage du Dao <Bean, typage Id>
        private fun getDao(jdbc: JdbcConnectionSource): Dao<UserBean, Long> =
                DaoManager.createDao(jdbc, UserBean::class.java)

        //Méthode pour sauvegarder un user
        fun insertUser(data: UserBean, jdbc: JdbcConnectionSource): Unit {
            data.user_session_id = Random.nextLong(0, 999999999999999999)
            getDao(jdbc).create(data)
        }

        fun pseudoAlreadyExists(pseudo: String, jdbc: JdbcConnectionSource): Boolean {
            return !getDao(jdbc).queryBuilder().where().eq("user_pseudo", pseudo).query().isNullOrEmpty()
        }

        //        Méthode pour vérifier l'existance d'un utilisateur
        fun verifyUser(data: UserBean, jdbc: JdbcConnectionSource): Boolean {
            return !getDao(jdbc).queryBuilder().where().eq("user_pseudo", data.user_pseudo).and().eq("user_mdp", data.user_mdp).query().isNullOrEmpty()
        }

        fun connectUser(data: UserBean, jdbc: JdbcConnectionSource) {
            data.user_session_id = Random.nextLong(0, 999999999999999999)
            var updateBuilder = getDao(jdbc).updateBuilder();
            updateBuilder.where().eq("user_pseudo", data.user_pseudo).and().eq("user_mdp", data.user_mdp)
            updateBuilder.updateColumnValue("user_session_id", data.user_session_id).update()
        }

        //Méthode pour trouver un user en fonction de son pseudo
        fun getUserIdWithSessionID(sessionID: Long?, jdbc: JdbcConnectionSource): UserBean? {
            val query = getDao(jdbc).queryBuilder().where().eq("user_session_id", sessionID).query()
            if (query.isNullOrEmpty()) {
                return null
            }
            return query[0]
        }

        //Méthode pour charger tous les user en Arraylist
        fun loadUsers(jdbc: JdbcConnectionSource) = ArrayList(getDao(jdbc).queryForAll())
    }
}