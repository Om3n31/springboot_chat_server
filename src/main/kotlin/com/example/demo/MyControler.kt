package com.example.demo

import com.example.demo.model.ErrorBean
import com.example.demo.model.MessageBean
import com.example.demo.model.UserBean
import org.springframework.web.bind.annotation.*


//Permet de transformer une class en WebService
@CrossOrigin
@RestController
class MyControler {


    //METHODE : RENVOIE UNE STRING AU WEB SERVICE
    //http://localhost:8080/test
    @CrossOrigin
    @GetMapping("/test")
    fun testMethode(): MessageBean {
        println("/test")
        var message = MessageBean(1, "test 2", 19, UserBean(1, "Thomas", 3245, "erer"))
        return message
    }

    @CrossOrigin
    @PostMapping("/messageGet")
    fun messageGet(@RequestBody user: UserBean): Any {
        println("/messageGet")
        val conn = DaoConnexion()
        try {
            if (user.user_session_id == null) {
                throw Exception("Session ID is null or empty")
            }
            if (UserDao.getUserIdWithSessionID(user.user_session_id, conn.connectionSource) == null) {
                throw Exception("This user does not exist")
            } else {
                //Sinon renvoie les 20 derniers messages
                return MessageDao.loadMessages(conn.connectionSource)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ErrorBean(e.message ?: "Une erreur est survenue")
        } finally {
            //Déconnexion du Dao
            conn.closeConnexion()
        }
    }

    @CrossOrigin
    @PostMapping("/messagePost")
    fun messagePost(@RequestBody messageBean: MessageBean): ErrorBean? {
        println("/messagePost")
        println(messageBean)
        val conn = DaoConnexion()
        try {
            //Renvoie un message d'erreur si pas de messages ou pseudo
            if (messageBean.message.isNullOrEmpty()) {
                throw Exception("Message invalide")
            }
            messageBean.user = UserDao.getUserIdWithSessionID(messageBean.user?.user_session_id, conn.connectionSource)
            if (messageBean.user == null) {
                throw Exception("This user does not exist")
            } else {
                println("saved")
                MessageDao.saveMessage(messageBean, conn.connectionSource)
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ErrorBean(e.message ?: "Une erreur est survenue")
        } finally {
            //Déconnexion du Dao
            conn.closeConnexion()
        }
    }

    @CrossOrigin
    @PostMapping("/inscription")
    fun userRegister(@RequestBody user: UserBean): Any {
        println("/inscription")
        println(user)
        val conn = DaoConnexion()
        try {
            if (user.user_mdp.isNullOrEmpty() || user.user_pseudo.isNullOrEmpty()) {
                throw Exception("Username or password is invalid")
            } else {
                if (UserDao.pseudoAlreadyExists(user.user_pseudo!!, conn.connectionSource)) {
                    return ErrorBean("This username is already taken")
                } else {
                    UserDao.insertUser(user, conn.connectionSource)
                    return UserBean(user.user_session_id)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return ErrorBean(e.message ?: "Something has happened")
        } finally {
            conn.closeConnexion()
        }
    }

    @CrossOrigin
    @PostMapping("/connexion")
    fun userConnexion(@RequestBody user: UserBean): Any? {
        println("/connexion")
        val conn = DaoConnexion()
        try {
            if (user.user_mdp.isNullOrEmpty() || user.user_pseudo.isNullOrEmpty()) {
                throw Exception("Username or password is invalid")
            }
            if (UserDao.verifyUser(user, conn.connectionSource)) {
                UserDao.connectUser(user, conn.connectionSource)
                return UserBean(user.user_session_id)
            } else {
                return ErrorBean("Password and Username don't match")
            }
        } catch (e: Exception) {
            return ErrorBean(e.message ?: "Something has happened")
        } finally {
            conn.closeConnexion()
        }
    }


}