package com.example.demo

import com.j256.ormlite.jdbc.JdbcConnectionSource

class DaoConnexion {
    var connectionSource = JdbcConnectionSource(
            Constante.URL, Constante.LOGIN, Constante.PASSWORD
    )

    fun closeConnexion() {
        connectionSource.close()
    }
}