package com.example.demo.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "users")
data class UserBean(

        @DatabaseField(generatedId = true)
        var user_id: Long? = null,

        @DatabaseField
        var user_pseudo: String?,

        @DatabaseField
        var user_session_id: Long? = null,

        @DatabaseField
        var user_mdp: String? = null) {

    constructor() : this(null, "", null, "")
    constructor(userSessionID: Long?) : this(null, "", userSessionID, null)

}