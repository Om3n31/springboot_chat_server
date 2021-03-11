package com.example.demo.model

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "messages")
data class MessageBean(

        @DatabaseField(generatedId = true)
        var id_message: Long? = null,

        @DatabaseField
        var message: String?,

        @DatabaseField
        var post_date: Long? = Date().time,

        @DatabaseField(foreignAutoRefresh = true, foreign = true)
        var user: UserBean?) {

    constructor(message: String?, user: UserBean?) : this(null, message, null, user)
    constructor() : this(null, "", 0, UserBean())

}